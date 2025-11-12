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
    del_yn TEXT DEFAULT 'N'                  -- 삭제 여부 ('Y' 또는 'N')
);