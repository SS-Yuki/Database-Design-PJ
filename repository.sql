CREATE TABLE IF NOT EXISTS IssueCase (	
	case_id INT UNSIGNED,
	type INT(10) UNSIGNED,
	severity INT(10) UNSIGNED,
	appear_commit_id INT(10) UNSIGNED,
	solve_commit_id INT(10) UNSIGNED, 
	primary key(case_id) 
);
CREATE TABLE IF NOT EXISTS IssueInstance (	
	inst_id INT UNSIGNED,
    case_id INT(10) UNSIGNED NOT NULL,
    commit_id INT(10) UNSIGNED NOT NULL,
    type INT(10) UNSIGNED,
    status INT(10) UNSIGNED NOT NULL,
    file_name VARCHAR(10), 
	primary key(inst_id) 
);
CREATE TABLE IF NOT EXISTS Commit (	
	commit_id INT UNSIGNED,
    branch_id INT(10) UNSIGNED,
    commiter VARCHAR(10),
    time DATETIME,
    hash VARCHAR(10), 
	primary key(commit_id) 
);
CREATE TABLE IF NOT EXISTS IssueLocation (	
	inst_id INT(10) UNSIGNED,
    `order` INT(10) UNSIGNED,
    start_line INT(10) UNSIGNED,
    end_line INT(10) UNSIGNED,
    start_col INT(10) UNSIGNED,
    end_col INT(10) UNSIGNED
);
CREATE TABLE IF NOT EXISTS Branch (	
	branch_id INT UNSIGNED,
	repository_id INT(10) UNSIGNED,
    name VARCHAR(10), 
	primary key(branch_id) 
);
CREATE TABLE IF NOT EXISTS Repository (	
	repository_id INT UNSIGNED,
    name VARCHAR(10),
    url VARCHAR(10), 
	primary key(repository_id) 
);