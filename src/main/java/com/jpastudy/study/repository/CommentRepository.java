package com.jpastudy.study.repository;

import com.jpastudy.study.domain.Comment;
import com.jpastudy.study.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Comment comment){
        em.persist(comment);
        return comment.getId();
    }

    public Comment find(Long id){
        return em.find(Comment.class, id);
    }

    public void delete(Long id){
        Comment comment = em.find(Comment.class, id);
        if(comment!=null) {
            em.remove(comment);
        }
    }
}
