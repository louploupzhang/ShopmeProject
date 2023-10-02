package com.shopme.admin.question;

import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends SearchRepository<Question, Integer> {
    @Query("select q from Question q where q.questionContent like %?1% or " +
            "q.answer like %?1% or q.product.name like %?1% or " +
            "concat(q.asker.firstName, ' ', q.asker.lastName) like %?1%")
    Page<Question> findAll(String keyword, Pageable pageable);

    @Query("update Question q set q.approved = ?2 where q.id = ?1")
    @Modifying
    public void updateApprovalStatus(Integer id, boolean enabled);
}
