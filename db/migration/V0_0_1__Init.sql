#------------------------------------------------------------------------------
#-- Table 명 : wisemil_member (와이즈밀 회원)
#------------------------------------------------------------------------------
CREATE TABLE wisemil_member
(
    id               bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    member_number    varchar(32)  NOT NULL COMMENT '회원번호',
    email            varchar(100) NULL COMMENT 'email',
    sign_type        varchar(30)  NOT NULL COMMENT '가입유형',
    role             varchar(20)  NOT NULL COMMENT '사용자 권한',
    status           varchar(10)  NOT NULL COMMENT '회원 계정 상태',
    member_detail_id bigint       NOT NULL COMMENT '회원 상세 id',

    created_by       varchar(64)  NULL COMMENT '생성자',
    created_at       datetime(6)  NOT NULL COMMENT '생성일시',
    modified_by      varchar(64)  NULL COMMENT '수정자',
    modified_at      datetime(6)  NOT NULL COMMENT '수정일시',
    version          bigint       NOT NULL COMMENT 'Data Version',

    CONSTRAINT pk_wisemil_member PRIMARY KEY (id),
    CONSTRAINT uk_wisemil_member_1 UNIQUE KEY (member_number),
    CONSTRAINT uk_wisemil_member_2 UNIQUE KEY (email, sign_type)
) ENGINE = InnoDB COMMENT ='와이즈밀 회원';

#------------------------------------------------------------------------------
#-- Table 명 : wisemil_member_detail (와이즈밀 회원 상세)
#------------------------------------------------------------------------------
CREATE TABLE wisemil_member_detail
(
    id          bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    password    varchar(300) NOT NULL COMMENT '암호화 된 비밀번호',
    nickname    varchar(32)  NULL COMMENT '닉네임',

    created_by  varchar(64)  NULL COMMENT '생성자',
    created_at  datetime(6)  NOT NULL COMMENT '생성일시',
    modified_by varchar(64)  NULL COMMENT '수정자',
    modified_at datetime(6)  NOT NULL COMMENT '수정일시',
    version     bigint       NOT NULL COMMENT 'Data Version',

    CONSTRAINT pk_wisemil_member_detail PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT ='와이즈밀 회원 상세';

#------------------------------------------------------------------------------
#-- Table 명 : member_sequence (memberNumber 시퀀스 테이블)
#------------------------------------------------------------------------------
CREATE TABLE member_sequence
(
    id       bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    date     date   NOT NULL COMMENT '생성일자',
    sequence bigint NOT NULL COMMENT 'sequence',

    CONSTRAINT pk_member_sequence PRIMARY KEY (id),
    INDEX idx_member_sequence_1 (date)
) ENGINE = InnoDB COMMENT ='memberNumber 시퀀스 테이블';
