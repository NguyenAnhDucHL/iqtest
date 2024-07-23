package com.study.iqtest.service.admin;

import com.study.iqtest.model.IqTestSetting;
import com.study.iqtest.repository.IqTestSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IqTestSettingService {
    @Autowired
    private IqTestSettingRepository iqTestSettingRepository;

    public IqTestSetting createSetting(IqTestSetting setting) {
        return iqTestSettingRepository.save(setting);
    }

    public IqTestSetting updateSetting(String id, IqTestSetting setting) {
        setting.setId(id);
        return iqTestSettingRepository.save(setting);
    }

    public void deleteSetting(String id) {
        iqTestSettingRepository.deleteById(id);
    }

    public List<IqTestSetting> getAllSettings() {
        return iqTestSettingRepository.findAll();
    }

    public IqTestSetting getSettingById(String id) {
        return iqTestSettingRepository.findById(id).orElse(null);
    }
}
