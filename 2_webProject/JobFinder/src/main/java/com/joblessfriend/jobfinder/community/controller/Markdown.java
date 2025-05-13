package com.joblessfriend.jobfinder.community.controller;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Markdown {

    public static String markdownToHtml(String markdown) {
    	// 먼저 ~~취소선~~ 텍스트를 <del>로 수동 변환
        markdown = markdown.replaceAll("~~(.*?)~~", "<del>$1</del>");
              
        // 이후, 변환된 마크다운을 commonmark로 처리
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        
        HtmlRenderer renderer = HtmlRenderer.builder()
                                           .escapeHtml(false)  // 이스케이프 비활성화
                                           .build();
        
        // HTML로 변환된 결과 반환
        return renderer.render(document);  // 마크다운을 HTML로 변환
    }
    
   

    
}
