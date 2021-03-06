package com.kh.bubblebee.notice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.kh.bubblebee.common.PageInfo;
import com.kh.bubblebee.common.Pagination;
import com.kh.bubblebee.member.model.vo.Member;
import com.kh.bubblebee.notice.model.exception.NoticeException;
import com.kh.bubblebee.notice.model.service.NoticeService;
import com.kh.bubblebee.notice.model.vo.Notice;
import com.kh.bubblebee.notice.model.vo.Pagination2;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService nService;
	
	@RequestMapping("list.no")
	public ModelAndView noticeList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv) {
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = nService.getNoticeListCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Notice> list = nService.selectNoticeList(pi);
		
		if(list != null) {
			// list, pi, view
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.setViewName("noticeList");
		} else {
			throw new NoticeException("공지사항 조회에 실패했습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping(value="insert.no", method = RequestMethod.POST)
	@ResponseBody
	public String insertNotice(@RequestParam("title") String title, @RequestParam("content") String content, Model model) {
		
		int result = nService.insertNotice(title, content);
		String bno = nService.selectBno(title, content);
		ArrayList<String> ids = nService.selectAllId();
		
		for(int i=0; i<ids.size(); i++) {
			nService.insertAlert(ids.get(i), title, bno);
		}
		
		if(result > 0) {
			/* return "redirect:list.no"; */
			return "success";
		} else {
			throw new NoticeException("공지사항 등록에 실패하였습니다.");
		}
	}
	
	@RequestMapping(value="insertFAQ.no", method = RequestMethod.POST)
	public String insertFAQ(@RequestParam("title") String title, @RequestParam("content") String content,
							@RequestParam("btype") int btype, Model model) {
		
		int result = nService.insertFAQ(title, content, btype);
		
		if(result > 0) {
			return "redirect:FAQList.no";
		} else {
			throw new NoticeException("FAQ 등록에 실패하였습니다.");
		}
	}
	
	@RequestMapping("FAQList.no")
	public ModelAndView FAQList(@RequestParam(value="page", required=false) Integer page, 
								@RequestParam(value="number", required=false) String number,
								@RequestParam(value="faqSearch", required=false) String faqSearch,
								ModelAndView mv) {
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		ArrayList<Notice> list = new ArrayList<>();
		PageInfo pi = new PageInfo();
		if(faqSearch == null) {
			int listCount = nService.getFAQListCount(number);
			
			pi = Pagination2.getPageInfo(currentPage, listCount);
			
			list = nService.selectFAQList(pi, number);
		} else {
			int listCount = nService.getFAQSearchListCount(faqSearch);
			pi = Pagination2.getPageInfo(currentPage, listCount);
			
			list = nService.selectFAQSearchList(pi, faqSearch);
		}
		
		
		if(list != null) {
			// list, pi, view
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.addObject("number", number);
			if(faqSearch != null) {
				mv.addObject("faqSearch", faqSearch);
			}
			mv.setViewName("FAQList");
		} else {
			throw new NoticeException("FAQ 조회에 실패했습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping("faqFilter.no")
	public void faq2List(@RequestParam(value="page", required=false) Integer page, @RequestParam(value="number", required=false) String number, HttpServletResponse response) {

		response.setContentType("application/json; charset=UTF-8");
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = nService.getFAQFilterListCount(number);
		
		PageInfo pi = Pagination2.getPageInfo(currentPage, listCount);
		
		ArrayList<Notice> list = nService.selectFAQFilterList(pi, number);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("pi", pi);
		map.put("list", list);
		map.put("number", number);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		try {
			gson.toJson(map, response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("deleteFAQ.no")
	public String deleteFAQ(@RequestParam("bno") String bno) {
		
		int result = nService.deleteFAQ(bno);
		
		if(result > 0) {
			return "redirect:FAQList.no";
		} else {
			throw new NoticeException("FAQ 삭제에 실패하였습니다.");
		}
	}
	
	@RequestMapping("updateFAQForm.no")
	public ModelAndView updateFAQForm(@RequestParam("bno") String bno,  ModelAndView mv) {
		
		Notice notice = nService.selectupdateFAQ(bno);
		
		if(notice != null) {
			mv.addObject("notice", notice);
			mv.setViewName("updateFAQForm");
		} else {
			throw new NoticeException("FAQ 조회에 실패하였습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping(value="updateFAQ.no", method = RequestMethod.POST) 
	public ModelAndView updateFAQ(@RequestParam("title") String title, @RequestParam("content") String content,
						@RequestParam("btype") int btype, @RequestParam("bno") String bno, HttpServletRequest request, ModelAndView mv) {
		
		int result = nService.updateFAQ(title, content, btype, bno);
		
		if(result > 0) {
			mv.addObject("number", btype);
			mv.setViewName("redirect:FAQList.no");
		} else {
			throw new NoticeException("FAQ 수정에 실패하였습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping(value="faqSearch.no")
	public void faqSerach(@RequestParam("faqSearch") String faqSearch, @RequestParam(value="page", required=false) Integer page, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = nService.getFAQSearchListCount(faqSearch);
		PageInfo pi = Pagination2.getPageInfo(currentPage, listCount);
		
		ArrayList<Notice> list = nService.selectFAQSearchList(pi, faqSearch);

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("pi", pi);
		map.put("list", list);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		try {
			gson.toJson(map, response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("deleteNotice.no")
	public String deleteNotice(@RequestParam("bno") String bno) {
		
		int result = nService.deleteFAQ(bno);	// 공지사항도 삭제가능
		
		if(result > 0) {
			return "redirect:list.no";
		} else {
			throw new NoticeException("공지사항 삭제에 실패하였습니다.");
		}
	}
	
	@RequestMapping("updateNoticeForm.no")
	public ModelAndView updateNoticeForm(@RequestParam("bno") String bno,  ModelAndView mv) {
		
		Notice notice = nService.selectupdateFAQ(bno);	// 공지사항도 조회가능
		
		if(notice != null) {
			mv.addObject("notice", notice);
			mv.setViewName("updateNoticeForm");
		} else {
			throw new NoticeException("공지사항 조회에 실패하였습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping(value="updateNotice.no", method = RequestMethod.POST) 
	public ModelAndView updateNotice(@RequestParam("title") String title, @RequestParam("content") String content,
						@RequestParam("bno") String bno, HttpServletRequest request, ModelAndView mv) {
		
		int result = nService.updateNotice(title, content, bno);
		
		if(result > 0) {
			/* mv.addObject("number", btype); */
			mv.setViewName("redirect:list.no");
		} else {
			throw new NoticeException("공지사항 수정에 실패하였습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping("noticeSelect")
	public ModelAndView noticeSelect(@RequestParam("bno") String bno, HttpSession session, ModelAndView mv) {
		String id = ((Member)session.getAttribute("loginUser")).getId();
		
		int result = nService.checkAlert(bno, id);
		Notice notice = nService.selectupdateFAQ(bno);
		if(notice != null) {
			if(result > 0) {
				mv.addObject("notice", notice);
				mv.setViewName("noticeSelect");
			} 
		} else {
			throw new NoticeException("공지사항 조회에 실패하였습니다.");
		}
		
		return mv;
	}
}
