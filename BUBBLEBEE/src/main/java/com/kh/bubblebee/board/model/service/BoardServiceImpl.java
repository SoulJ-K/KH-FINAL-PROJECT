package com.kh.bubblebee.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.bubblebee.board.model.dao.BoardDAO;
import com.kh.bubblebee.board.model.vo.Board;
import com.kh.bubblebee.board.model.vo.Option;
import com.kh.bubblebee.board.model.vo.Review;
import com.kh.bubblebee.common.PageInfo;
import com.kh.bubblebee.host.model.vo.Host;

@Service("bService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO bDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int getListCount(String cate) {
		return bDAO.getListCount(sqlSession, cate);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi, HashMap<String, String> map) {
		return bDAO.selectList(sqlSession, pi, map);
	}

	@Override
	public ArrayList<Board> selectLtList(HashMap<String, String> map, PageInfo pi) {
		return bDAO.selectLtList(sqlSession, map, pi);
	}

	@Override
	public ArrayList<Board> selectHtList(HashMap<String, String> map, PageInfo pi) {
		return bDAO.selectHtList(sqlSession, map, pi);
	}

	@Override
	public int insertBoard(Board b) {
		// TODO Auto-generated method stub
		return bDAO.insertBoard(sqlSession, b);
	}
	
	@Override
	public int insertBoardOption(HashMap<String, Option> map) {
		// TODO Auto-generated method stub
		return bDAO.insertBoardOption(sqlSession, map);
	}
	
	@Override
	public Board selectBoard(int fno) {
		return bDAO.selectBoard(sqlSession, fno);
	}
	
	@Override
	public int boardUpdate(Board b) {
		// TODO Auto-generated method stub
		return bDAO.updateBoard(sqlSession, b);
	}

	@Override
	public int boardUpdateOption(HashMap<String, Option> map) {
		// TODO Auto-generated method stub
		return bDAO.updateBoardOption(sqlSession, map);
	}
	
	@Override
	public Host selectHost(String hostId) {
		return bDAO.selectHost(sqlSession, hostId);
	}

  @Override
	public int getSearchListCount(HashMap<String, Object> map) {
		return bDAO.getSearchListCount(sqlSession, map);
	}

	@Override
	public ArrayList<Board> selectSearchList(HashMap<String, Object> map, PageInfo pi) {
		return bDAO.selectSearchList(sqlSession, map, pi);
	}

	@Override
	public int insertHeart(HashMap<String, Object> map) {
		return bDAO.insertHeart(sqlSession, map);
	}

	@Override
	public int readHeart(HashMap<String, Object> map) {
		return bDAO.readHeart(sqlSession, map);
	}

	@Override
	public int deleteHeart(HashMap<String, Object> map) {
		return bDAO.deleteHeart(sqlSession, map);
	}

	@Override
	public ArrayList<Board> selectFindList(String search) {
		return bDAO.selectFindList(sqlSession, search);
	}

	@Override
	public int getFindListCount(String search) {
		return bDAO.getFindListCount(sqlSession, search);
	}

	@Override
	public ArrayList<Board> selectwList(String cate) {
		return bDAO.selectwList(sqlSession, cate);
	}

	@Override
	public ArrayList<Board> markLocation() {
		return bDAO.markLocation(sqlSession);
	}

	@Override
	public int deleteBoard(int fno) {
		// TODO Auto-generated method stub
		return bDAO.deleteBoard(sqlSession, fno);
	}

	@Override
	public int deleteBoardOption(int fno) {
		// TODO Auto-generated method stub
		return bDAO.deleteBoardOption(sqlSession, fno);
	}
	
	@Override
	public int plusHeart(int fno) {
		return bDAO.plusHeart(sqlSession, fno);
	}

	@Override
	public int minusHeart(int fno) {
		return bDAO.minusHeart(sqlSession, fno);
	}

	@Override
	public ArrayList<Board> selectMList() {
		return bDAO.selectMList(sqlSession);
	}

	@Override
	public ArrayList<Board> selectCList() {
		return bDAO.selectCList(sqlSession);
	}

	@Override
	public ArrayList<Board> selectUpMList() {
		return bDAO.selectUpMList(sqlSession);
	}

	@Override
	public ArrayList<Board> selectUpCList() {
		return bDAO.selectUpCList(sqlSession);
	}

	@Override
	public ArrayList<Option> selectOption(int fno) {
		return bDAO.selectOption(sqlSession,fno);
	}

	@Override
	public ArrayList<Integer> selectHeartList(String id) {
		return bDAO.selectHeartList(sqlSession, id);
	}

	@Override
	public int getFindFilterListCount(HashMap<String, Object> map) {
		return bDAO.getFindFilterListCount(sqlSession, map);
	}

	@Override
	public ArrayList<Board> selectFindFilterList(HashMap<String, Object> map) {
		return bDAO.selectFindFilterList(sqlSession, map);
	}

	@Override
	public ArrayList<Board> selectwFindFilterList(String search) {
		return bDAO.selectwFindFilterList(sqlSession, search);
	}

	@Override
	public int getClassListCount() {
		return bDAO.getClassListCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectClassList(PageInfo pi) {
		return bDAO.selectClassList(sqlSession, pi);
	}

	@Override
	public int agreeClass(String fno) {
		return bDAO.agreeClass(sqlSession, fno);
	}

	@Override
	public int rejectClass(String fno) {
		return bDAO.rejectClass(sqlSession, fno);
	}

	@Override
	public int updateOption() {
		// TODO Auto-generated method stub
		return bDAO.updateOption(sqlSession);
	}

	@Override
	public Review selectReview(int fno) {
		return bDAO.selectReview(sqlSession, fno);
	}
	
}
