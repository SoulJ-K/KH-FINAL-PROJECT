package com.kh.bubblebee.board.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.bubblebee.board.model.exception.BoardException;
import com.kh.bubblebee.board.model.vo.Board;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.kh.bubblebee.board.model.service.BoardService;
import com.kh.bubblebee.common.PageInfo;
import com.kh.bubblebee.common.Pagination;

@Controller
public class BoardInputController {
	
	@Autowired
	private BoardService bService;
	

	@RequestMapping("binsert.bo")
	public String boardInsert(@ModelAttribute Board b, @RequestParam("uploadFile") List<MultipartFile> uploadFile, HttpServletRequest request) {
		
		String[] originalFileName = new String[uploadFile.size()];
		
		for(int i = 0; i<uploadFile.size(); i++) {
			originalFileName[i] = uploadFile.get(i).getOriginalFilename();
		}
		
		if(uploadFile != null && !uploadFile.isEmpty()) {
			//파일이 있다면! 
			String[] renameFileName = saveFile(uploadFile, request);
			String origin = "";
			String rename = "";
			int i = 0;
			if(renameFileName != null) {
				origin = String.join(",", originalFileName);
				rename = String.join(",", renameFileName);
				b.setOriginalFileName(origin);
				b.setRenameFileName(rename);
			}
					
		}
		
		int result = bService.insertBoard(b);
		
		if(result > 0) {
			return "redirect:blist.bo";
		}else {
			throw new BoardException("게시글 등록 실패!");
		}
	
	}


	private String[] saveFile(List<MultipartFile> file, HttpServletRequest request) {
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
		
		File folder = new File(savePath);
		
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String[] renameFileName = new String[file.size()]; 
		String[] originalFileName = new String[file.size()] ;
		
		while(i < file.size()) {
			String renamePath = folder + "\\";
			originalFileName[i] = file.get(i).getOriginalFilename(); // 원본 이름 넣는 곳
			renameFileName[i] = sdf.format(new Date(System.currentTimeMillis())) + "(" + i + ")" +"."
					+ originalFileName[i].substring(originalFileName[i].lastIndexOf(".") + 1);
			
			renamePath += renameFileName[i];
			try {
				file.get(i).transferTo(new File(renamePath));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
		return renameFileName;
	}
	}