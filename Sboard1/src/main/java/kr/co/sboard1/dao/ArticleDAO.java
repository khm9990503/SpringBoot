package kr.co.sboard1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.sboard1.vo.ArticleVO;
import kr.co.sboard1.vo.FileVO;

@Mapper
@Repository
public interface ArticleDAO {

	public int insertArticle(ArticleVO vo); // int는 실행 결과의 raw 갯수 
	public int insertFile(FileVO vo);
	public ArticleVO selectArticle(int no);
	public List<ArticleVO> selectArticles(int start);
	public FileVO selectFile(int fno);
	public int updateFileDownload(int fno);
	public void updateArticle(ArticleVO vo);
	public void updateArticleHit(int no);
	public void deleteArticle(int no);
	public int selectCountTotal();
}
