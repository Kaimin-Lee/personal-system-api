package com.personal.system.service;

import com.personal.system.entity.Memo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 生活备忘录表 服务类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
public interface IMemoService extends IService<Memo> {

    List<Memo> getMyMemos(Long userId, String keyword);

    void togglePin(Long id, Long userId);
}
