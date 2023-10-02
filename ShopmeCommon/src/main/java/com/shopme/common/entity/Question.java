package com.shopme.common.entity;

import com.shopme.common.entity.Customer;
import com.shopme.common.entity.IdBasedEntity;
import com.shopme.common.entity.Review;
import com.shopme.common.entity.User;
import com.shopme.common.entity.product.Product;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class Question extends IdBasedEntity {
    @Column(name = "question")
    private String questionContent;

    private String answer;
    private int votes;
    private boolean approved;

    @Column(name = "ask_time")
    private Date askTime;

    @Column(name = "answer_time")
    private Date answerTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "answerer_id")
    private User answerer;

    @ManyToOne
    @JoinColumn(name = "asker_id")
    private Customer asker;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Date getAskTime() {
        return askTime;
    }

    public void setAskTime(Date askTime) {
        this.askTime = askTime;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getAnswerer() {
        return answerer;
    }

    public void setAnswerer(User answerer) {
        this.answerer = answerer;
    }

    public Customer getAsker() {
        return asker;
    }

    public void setAsker(Customer asker) {
        this.asker = asker;
    }

    @Transient
    public boolean isAnswered() {
        return this.answer != null && !answer.isEmpty();
    }

    @Transient
    public String getProductName() {
        return this.product.getName();
    }

    @Transient
    public String getAskerFullName() {
        return asker.getFirstName() + " " + asker.getLastName();
    }

    @Transient
    public String getAnswererFullName() {
        if (answerer != null) {
            return answerer.getFirstName() + " " + answerer.getLastName();
        }
        return "";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        return Objects.equals(id, other.id);
    }

    @Transient
    private boolean upvotedByCurrentCustomer;

    @Transient
    private boolean downvotedByCurrentCustomer;

    public boolean isUpvotedByCurrentCustomer() {
        return upvotedByCurrentCustomer;
    }

    public void setUpvotedByCurrentCustomer(boolean upvotedByCurrentCustomer) {
        this.upvotedByCurrentCustomer = upvotedByCurrentCustomer;
    }

    public boolean isDownvotedByCurrentCustomer() {
        return downvotedByCurrentCustomer;
    }

    public void setDownvotedByCurrentCustomer(boolean downvotedByCurrentCustomer) {
        this.downvotedByCurrentCustomer = downvotedByCurrentCustomer;
    }
}
