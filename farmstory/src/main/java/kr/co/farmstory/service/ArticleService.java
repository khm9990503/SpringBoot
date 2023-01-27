package kr.co.farmstory.service;

import kr.co.farmstory.dao.ArticleDAO;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleDAO dao;

    public int insertArticle(ArticleVO vo) {

        // 글 등록
        int result = dao.insertArticle(vo);
        // 파일 업로드
        FileVO fvo = fileUpload(vo);
        // 파일 등록
        if(fvo != null) {
            dao.insertFile(fvo);
        }

        return result;
    };
    @Transactional
    public ArticleVO insertComment(ArticleVO vo){
        dao.insertComment(vo);
        dao.updateCommentCountPLS(vo.getParent());
        return dao.selectCommentLatest();
    };
    public ArticleVO selectArticle(int no) {
        return dao.selectArticle(no);
    };
    public List<ArticleVO> selectArticles(int start,String cate) {
        return dao.selectArticles(start,cate);
    };
    public FileVO selectFile(int fno) {
        return dao.selectFile(fno);
    };
    public List<ArticleVO> selectArticlesIDX(String cate){
        return dao.selectArticlesIDX(cate);
    };
    public int updateFileDownload(int fno) {
        return dao.updateFileDownload(fno);
    };
    public void updateArticle(ArticleVO vo) {
        dao.updateArticle(vo);
    };
    public void updateArticleHit(int no) {
        dao.updateArticleHit(no);
    };
    public void deleteArticle(int no) {
        dao.deleteArticle(no);
    };
    // 파일 다운로드 //////////////////////////////////////////////////////
    public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {
        // String path = new File(uploadPath).getAbsolutePath()+"/"+vo.getNewName();
        Path path = Paths.get(uploadPath+vo.getNewName());
        String contentType = Files.probeContentType(path);
        HttpHeaders headers = new HttpHeaders(); // 스프링에서 제공 : HttpHeaders, ResponseEntity
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename(vo.getOriName(), StandardCharsets.UTF_8)
                .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK); // res 객체
    }
    // 파일 업로드 ///////////////////////////////////////////////////////
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath; // 프로젝트 내 가상 경로

    public FileVO fileUpload(ArticleVO vo) {
        // 첨부 파일
        MultipartFile file = vo.getFname();
        FileVO fvo = null;

        if(!file.isEmpty()) {
            // 시스템 경로
            String path = new File(uploadPath).getAbsolutePath();

            // 새 파일명 생성
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String nName = UUID.randomUUID().toString()+ext;

            // 파일 저장
            try {
                file.transferTo(new File(path, nName));
            } catch (IllegalStateException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            fvo = FileVO.builder()
                    .parent(vo.getNo())
                    .oriName(oName)
                    .newName(nName)
                    .build();
        }

        return fvo;
    }
    // 페이징 처리 시작 ///////////////////////////////////////////////////////
    // 현재 페이지 번호
    public int getCurrnetPage(String pg) {
        int currentPage = 1;
        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }
    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage-1) * 10;
    }
    // 게시물 총 갯수
    public int selectCountTotal(String cate) {
        return dao.selectCountTotal(cate);
    }
    // 마지막 페이지 번호
    public int getLastPageNum(int total) {
        int lastPageNum = 0;
        if(total % 10 == 0){
            lastPageNum = total / 10;
        }else{
            lastPageNum = total / 10 + 1;
        }
        return lastPageNum;
    }
    // 시작 페이지 번호
    public int getPageStartNum(int total, int start) {
        return total - start;
    }
    // 페이지 그룹
    public int[] getPageGroup(int currentPage, int lastPageNum) {
        int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
        int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
        int pageGroupEnd = currentPageGroup * 10;
        if(pageGroupEnd > lastPageNum){
            pageGroupEnd = lastPageNum;
        }
        int[] result = {pageGroupStart,pageGroupEnd};
        return result;
    }
    // 페이징 처리 끝 ///////////////////////////////////////////////////////
    // 댓글  ///////////////////////////////////////////////////////
    public List<ArticleVO> selectComments(int no){
        return dao.selectComments(no);
    };
    public int updateComment(String content, int no){
        return dao.updateComment(content, no);
    };
    @Transactional
    public int deleteComment(int parent, int no){
        dao.updateCommentCountMNS(parent);
        return dao.deleteComment(no);
    };

}
