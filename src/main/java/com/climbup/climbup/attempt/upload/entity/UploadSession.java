package com.climbup.climbup.attempt.upload.entity;

import com.climbup.climbup.attempt.upload.enums.UploadStatus;
import com.climbup.climbup.common.entity.BaseEntity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "upload_sessions")
@AllArgsConstructor
public class UploadSession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UploadStatus status;

    @Column(name = "chunk_length")
    private Integer chunkLength;

    @Column(name = "chunk_size")
    private Long chunkSize;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @OneToMany(mappedBy = "uploadSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Chunk> chunks = new HashSet<>();

    public Long getReceivedChunkCount() {
        return this.chunks.stream().filter(Chunk::isCompleted).count();
    }

    public List<Integer> getReceivedChunkList() {
        return this.chunks.stream().map(Chunk::getChunkIndex).toList();
    }
}
