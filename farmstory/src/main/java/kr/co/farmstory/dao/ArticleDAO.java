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
    public ArticleVO selectArticle(int no);
    public int insertFile(FileVO vo);
    public void insertComment(ArticleVO vo);
    public List<ArticleVO> selectArticles(@Param("start") int start, @Param("cate") String cate);
    public FileVO selectFile(int fno);
    public ArticleVO selectCommentLatest();
    public int updateFileDownload(int fno);
    public void updateArticle(ArticleVO vo);
    public void updateArticleHit(int no);
    public void updateCommentCountPLS(int parent);
    public void updateCommentCountMNS(int parent);
    public int updateComment(@Param("content") String content, @Param("no") int no);
    public void deleteArticle(int no);
    public int deleteComment(int no);
    public int selectCountTotal(String cate);
    public List<ArticleVO> selectComments(int no);
    public List<ArticleVO> selectArticlesIDX(String cate);
    public List<ArticleVO> selectLatest(String cate);
}
