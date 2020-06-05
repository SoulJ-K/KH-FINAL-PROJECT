package com.kh.bubblebee.notice.model.service;

import java.util.ArrayList;

import com.kh.bubblebee.common.PageInfo;
import com.kh.bubblebee.notice.model.vo.Notice;

public interface NoticeService {

	int insertNotice(String title, String content);

	int getListCount();

	ArrayList<Notice> selectList(PageInfo pi);

}
