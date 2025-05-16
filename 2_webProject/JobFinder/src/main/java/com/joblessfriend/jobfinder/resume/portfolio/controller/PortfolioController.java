package com.joblessfriend.jobfinder.resume.portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.resume.portfolio.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.portfolio.service.PortfolioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/portfolio")
@Controller
public class PortfolioController {

	@Autowired
	private PortfolioService portfolioService;
	
	@GetMapping("/list")
    public String getPortfolioList(Model model) {
		List<PortfolioVo> portfolioList = portfolioService.portfolioAllList();
		
		
		return null;
	}
	
}
