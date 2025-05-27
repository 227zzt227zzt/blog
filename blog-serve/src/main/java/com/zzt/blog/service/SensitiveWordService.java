package com.zzt.blog.service;

import com.zzt.blog.dto.SensitiveWordDTO;
import com.zzt.blog.entity.SensitiveWord;
import java.util.List;

public interface SensitiveWordService {
    List<SensitiveWord> listSensitiveWords();
    SensitiveWord getSensitiveWordById(Integer id);
    void saveSensitiveWord(SensitiveWordDTO sensitiveWord);
    void updateSensitiveWord(SensitiveWord sensitiveWord);
    void deleteSensitiveWord(Integer id);
    String filterContent(String content);
}