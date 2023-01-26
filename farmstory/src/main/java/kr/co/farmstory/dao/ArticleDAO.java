package kr.co.farmstory.dao;

import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO {

    public int insertArticle(ArticleVO vo); // int는 실행 결과의 raw 갯수
    public int insertFile(FileVO vo);
    public ArticleVO selectArticle(int no);
    public List<ArticleVO> selectArticles(@Param("start") int start, @Param("cate") String cate);
    public FileVO selectFile(int fno);
    public int updateFileDownload(int fno);
    public void updateArticle(ArticleVO vo);
    public void updateArticleHit(int no);
    public void deleteArticle(int no);
    public int selectCountTotal(String cate);
}
