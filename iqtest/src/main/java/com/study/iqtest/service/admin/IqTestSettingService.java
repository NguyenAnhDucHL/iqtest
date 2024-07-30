package com.study.iqtest.service.admin;

import com.study.iqtest.dto.IqTestQuestionDTO;
import com.study.iqtest.dto.IqTestSettingDTO;
import com.study.iqtest.exception.ResourceNotFoundException;
import com.study.iqtest.mapper.IqTestSettingMapper;
import com.study.iqtest.model.IqTestQuestion;
import com.study.iqtest.model.IqTestSetting;
import com.study.iqtest.repository.IqTestQuestionRepository;
import com.study.iqtest.repository.IqTestSettingRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IqTestSettingService {

    @Autowired
    private IqTestSettingRepository iqTestSettingRepository;

    @Autowired
    private IqTestQuestionRepository iqTestQuestionRepository;

    @Autowired
    private IqTestSettingMapper iqTestSettingMapper;

    @Autowired
    private IqTestQuestionService iqTestQuestionService;

    @Autowired
    private IqTestAnswerService iqTestAnswerService;

    @Transactional
    public IqTestSettingDTO createSettingWithQuestions(IqTestSettingDTO dto) {
        IqTestSetting setting = iqTestSettingMapper.toModal(dto);
        setting.setCreatedAt(new Date());
        setting.setUpdatedAt(new Date());
        setting = iqTestSettingRepository.save(setting);

        final ObjectId settingId = setting.getId();
        List<IqTestQuestionDTO> listQuestions = new ArrayList<>();

        dto.getQuestions().forEach(question -> {
            question.setTestSettingId(settingId);
            IqTestQuestionDTO iqTestQuestionDTO = iqTestQuestionService.createQuestion(question);
            listQuestions.add(iqTestQuestionDTO);
        });

        IqTestSettingDTO iqTestSettingDTO = iqTestSettingMapper.toDto(setting);
        iqTestSettingDTO.setQuestions(listQuestions);
        return iqTestSettingDTO;
    }

    @Transactional
    public IqTestSettingDTO updateSetting(ObjectId id, IqTestSettingDTO settingDto) {
        Optional<IqTestSetting> existingSetting = iqTestSettingRepository.findById(id.toHexString());
        if (existingSetting.isPresent()) {
            IqTestSetting setting = existingSetting.get();

            setting.setSettingName(settingDto.getSettingName());
            setting.setUpdatedAt(new Date());

            iqTestQuestionRepository.deleteByTestSettingId(setting.getId());

            setting = iqTestSettingRepository.save(setting);

            List<IqTestQuestionDTO> updatedQuestions = new ArrayList<>();
            for (IqTestQuestionDTO questionDto : settingDto.getQuestions()) {
                IqTestQuestionDTO updatedQuestion = iqTestQuestionService.createQuestion(questionDto);
                updatedQuestions.add(updatedQuestion);
            }

            settingDto.setQuestions(updatedQuestions);

            IqTestSettingDTO result = iqTestSettingMapper.toDto(setting);
            result.setQuestions(updatedQuestions);

            return result;
        } else {
            throw new ResourceNotFoundException("Setting not found with id: " + id);
        }
    }

    @Transactional
    public void deleteSettingById(ObjectId testSettingId) {
        List<IqTestQuestion> questions = iqTestQuestionRepository.findByTestSettingId(testSettingId);

        List<ObjectId> questionIds = questions.stream()
                .map(IqTestQuestion::getId)
                .collect(Collectors.toList());

        if (!questionIds.isEmpty()) {
            iqTestAnswerService.deleteAnswersByQuestionIds(questionIds);
        }

        iqTestQuestionRepository.deleteByTestSettingId(testSettingId);

        iqTestSettingRepository.deleteById(testSettingId.toHexString());
    }

    public List<IqTestSettingDTO> getAllSettings() {
        return iqTestSettingRepository.findAll().stream()
                .map(iqTestSetting -> {
                    IqTestSettingDTO settingDTO = iqTestSettingMapper.toDto(iqTestSetting);
                    List<IqTestQuestionDTO> questions = iqTestQuestionService.getQuestionsBySettingId(iqTestSetting.getId());
                    settingDTO.setQuestions(questions);
                    return settingDTO;
                })
                .collect(Collectors.toList());
    }

    public IqTestSettingDTO getSettingById(ObjectId id) {
        return iqTestSettingRepository.findById(id.toHexString())
                .map(iqTestSetting -> {
                    IqTestSettingDTO settingDTO = iqTestSettingMapper.toDto(iqTestSetting);
                    List<IqTestQuestionDTO> questions = iqTestQuestionService.getQuestionsBySettingId(id);
                    settingDTO.setQuestions(questions);
                    return settingDTO;
                })
                .orElse(null);
    }
}
