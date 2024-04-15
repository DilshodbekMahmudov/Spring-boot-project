package com.lesson.Springboot.repository;

import com.lesson.Springboot.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage,Long> {

    FileStorage findByHashId(String hashId);


}
