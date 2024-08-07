package com.whatpl.project.service;

import com.whatpl.project.domain.ProjectParticipant;
import com.whatpl.project.domain.ProjectSkill;
import com.whatpl.project.domain.RecruitJob;
import com.whatpl.project.dto.ProjectInfo;
import com.whatpl.project.dto.RemainedJobDto;
import com.whatpl.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ProjectReadAsyncService {

    private final ProjectRepository projectRepository;
    private static final ExecutorService executor = Executors.newWorkStealingPool();

    /**
     * projectInfos 를 순회하면서 skills 필드를 set 합니다.
     */
    public CompletableFuture<Void> mergeSkills(List<ProjectInfo> projectInfos) {
        return CompletableFuture.supplyAsync(() -> projectRepository.findProjectSkillMap(toProjectIds(projectInfos)), executor)
                .thenAccept(skillMap -> projectInfos.forEach(projectInfo -> {
                    long projectId = projectInfo.getProjectId();
                    projectInfo.setSkills(skillMap.get(projectId)
                            .stream()
                            .map(ProjectSkill::getSkill).toList());
                }));
    }

    /**
     * projectInfos 를 순회하면서 remainedJobs 필드를 set 합니다.
     * getRecruitJobs 메서드와 getParticipants 메서드를 비동기로 실행하고, 실행결과를 조합합니다.
     */
    public CompletableFuture<Void> mergeRemainedJobs(List<ProjectInfo> projectInfos) {
        return CompletableFuture.supplyAsync(() -> projectRepository.findRecruitJobMap(toProjectIds(projectInfos)), executor)
                .thenCombine(getParticipantFuture(projectInfos), (recruitJobMap, participantMap) -> {
                    projectInfos.forEach(projectInfo -> {
                        long projectId = projectInfo.getProjectId();
                        List<RecruitJob> recruitJobs = recruitJobMap.get(projectId);
                        List<ProjectParticipant> participants = Optional.ofNullable(participantMap.get(projectId)).orElseGet(Collections::emptyList);
                        List<RemainedJobDto> remainedJobs = recruitJobs.stream()
                                .map(recruitJob -> RemainedJobDto.builder()
                                        .job(recruitJob.getJob())
                                        .recruitAmount(recruitJob.getRecruitAmount())
                                        .remainedAmount(recruitJobs.size() - participants.size())
                                        .build())
                                .filter(remainedJob -> remainedJob.getRemainedAmount() != 0)
                                .toList();
                        projectInfo.setRemainedJobs(remainedJobs);
                    });
                    return null;
                });
    }

    /**
     * projectInfos 를 순회하면서 likes 필드를 set 합니다.
     */
    public CompletableFuture<Void> mergeLikes(List<ProjectInfo> projectInfos) {
        return CompletableFuture.supplyAsync(() -> projectRepository.findProjectLikeMap(toProjectIds(projectInfos)), executor)
                .thenAccept(likeMap -> projectInfos.forEach(projectInfo -> {
                    long projectId = projectInfo.getProjectId();
                    int likes = Optional.ofNullable(likeMap.get(projectId)).orElseGet(Collections::emptyList).size();
                    projectInfo.setLikes(likes);
                }));
    }

    /**
     * projectInfos 를 순회하면서 comments 필드를 set 합니다.
     */
    public CompletableFuture<Void> mergeComments(List<ProjectInfo> projectInfos) {
        return CompletableFuture.supplyAsync(() -> projectRepository.findProjectCommentMap(toProjectIds(projectInfos)), executor)
                .thenAccept(commentMap -> projectInfos.forEach(projectInfo -> {
                    long projectId = projectInfo.getProjectId();
                    int comments = Optional.ofNullable(commentMap.get(projectId)).orElseGet(Collections::emptyList).size();
                    projectInfo.setComments(comments);
                }));
    }

    private CompletableFuture<Map<Long, List<ProjectParticipant>>> getParticipantFuture(List<ProjectInfo> projectInfos) {
        return CompletableFuture.supplyAsync(() -> projectRepository.findParticipantMap(toProjectIds(projectInfos)), executor);
    }

    private List<Long> toProjectIds(List<ProjectInfo> projectInfos) {
        return projectInfos.stream()
                .map(ProjectInfo::getProjectId)
                .toList();
    }
}
