package com.climbup.climbup.attempt.upload.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chunks")
public class Chunk extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "index")
    private Integer chunkIndex;

    @Column(name = "name")
    private String name;

    @Column(name = "is_completed")
    private boolean isCompleted = false;

    @Column(name = "chunk_size")
    private Long chunkSize;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_session_id", nullable = false)
    private UploadSession uploadSession;
}
