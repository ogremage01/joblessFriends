package com.joblessfriend.jobfinder.address.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/address")
public class AddressController {
	
	@Value("${api.juso.key}")
    private String jusoApiKey;
	
	@GetMapping("/popup")
	public String openAddressPopup (Model model) {
		model.addAttribute("jusoApiKey", jusoApiKey); // jsp로 전달
		
		 // /WEB-INF/views/address/addressPopup.jsp 를 반환
		return "resume/resumeView";
	}

}
