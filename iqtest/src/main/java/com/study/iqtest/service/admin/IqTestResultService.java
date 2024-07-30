package com.study.iqtest.service.admin;

import com.study.iqtest.dto.IqTestResultDTO;
import com.study.iqtest.exception.IqTestException;
import com.study.iqtest.model.IqTest;
import com.study.iqtest.model.IqTestResult;
import com.study.iqtest.model.User;
import com.study.iqtest.repository.IqTestRepository;
import com.study.iqtest.repository.IqTestResultRepository;
import com.study.iqtest.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IqTestResultService {

    @Autowired
    private IqTestResultRepository iqTestResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IqTestRepository iqTestRepository;

    public Page<IqTestResultDTO> searchIqTestResults(String searchText, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "resultDate"));
        List<User> users = userRepository.findBySearchText(searchText);
        List<ObjectId> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        List<IqTest> iqTests = iqTestRepository.findByUserIdIn(userIds);
        List<ObjectId> testIds = iqTests.stream().map(IqTest::getId).collect(Collectors.toList());

        Map<ObjectId, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        return iqTestResultRepository.findByTestIdIn(testIds, pageable).map(result -> convertToIqTestResultDTO(result, userMap));
    }

    private IqTestResultDTO convertToIqTestResultDTO(IqTestResult result, Map<ObjectId, User> userMap) {
        IqTest iqTest = iqTestRepository.findById(result.getTestId()).orElseThrow(() -> new IqTestException("Test not found"));
        User user = userMap.get(iqTest.getUserId());

        return new IqTestResultDTO(
                result.getId(),
                result.getTestId(),
                iqTest.getUserId(),
                result.getScore(),
                result.getResultDate(),
                result.getFeedback(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
