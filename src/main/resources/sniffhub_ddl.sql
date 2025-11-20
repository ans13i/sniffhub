-- 보호자 테이블
CREATE TABLE IF NOT EXISTS owner (
    id INTEGER PRIMARY KEY AUTOINCREMENT,     -- 보호자 ID (PK)
    name TEXT NOT NULL,                       -- 이름
    address TEXT,                             -- 주소
    phone TEXT,                               -- 연락처
    del_yn TEXT DEFAULT 'N'                   -- 삭제 여부 ('Y' 또는 'N')
);

-- 강아지 테이블
CREATE TABLE IF NOT EXISTS dog (
    id INTEGER PRIMARY KEY AUTOINCREMENT,     -- 강아지 ID (PK)
    name TEXT NOT NULL,                       -- 이름
    age INTEGER NOT NULL,                     -- 나이
    size TEXT,                                -- 크기 (소형/대형)
    breed TEXT,                               -- 품종
    klass TEXT,                               -- 반 이름 (사회반 / 놀이반 / 교육반)
    owner_id INTEGER,                         -- 보호자 ID (FK)
    del_yn TEXT DEFAULT 'N'                   -- 삭제 여부 ('Y' 또는 'N')
);

-- 강아지 출석관리 테이블
CREATE TABLE IF NOT EXISTS dog_attendance (
    id INTEGER PRIMARY KEY AUTOINCREMENT,     -- 출결 PK
    dog_id INTEGER NOT NULL,                  -- 강아지 PK (FK)
    attendance_date TEXT NOT NULL,            -- 출석 일자 (yyyy-MM-dd)
    is_present INTEGER NOT NULL,              -- 출석 여부 (0 / 1)
    ate_meal INTEGER NOT NULL,                -- 식사 여부 (0 / 1)
    training_level TEXT,                      -- 훈련 참여도 (상/중/하 등)
    del_yn CHAR(1) DEFAULT 'N',               -- 삭제 여부 ('Y' 또는 'N')
    created_at TEXT DEFAULT (datetime('now','localtime')),
    updated_at TEXT,
    -- 하나의 강아지에 대해 하루에 한 번만 출석 저장 가능
    UNIQUE (dog_id, attendance_date)
);