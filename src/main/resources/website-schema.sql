DROP TABLE IF EXISTS files CASCADE;
DROP TABLE IF EXISTS mimetypes CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS tags CASCADE;
DROP TABLE IF EXISTS tagtypes CASCADE;
DROP TABLE IF EXISTS poststatuses CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS postactions CASCADE;
DROP TABLE IF EXISTS postlog CASCADE;
DROP TABLE IF EXISTS posttags CASCADE;
DROP TABLE IF EXISTS attachmenttypes CASCADE;
DROP TABLE IF EXISTS attachments CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS fileextensions CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS postcategories CASCADE;
DROP TABLE IF EXISTS commentstatuses CASCADE;
DROP TABLE IF EXISTS countries CASCADE;
DROP TABLE IF EXISTS stats CASCADE;

  
-- Users contain all the users and their data
-- this system does not exhibit role syste so all authenticated users
-- are authorized to edit any content
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email VARCHAR(128) UNIQUE NOT NULL,
  passwordHash VARCHAR(128) NOT NULL,
  blocked BOOLEAN NOT NULL DEFAULT FALSE,
  displayName VARCHAR(128),
  description TEXT,
  created TIMESTAMP NOT NULL,
  updated TIMESTAMP NOT NULL,
  created_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  updated_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  CHECK(updated >= created)
);

INSERT INTO users (email, passwordHash, blocked, displayName, description, created, updated) VALUES ('admin@local', crypt('admin', gen_salt('md5')), FALSE, 'Admin', 'Default admin user', localtimestamp, localtimestamp);

-- Contains list of mimetypes supported by the system.
-- With each mimetype you can specify uri for the icon,
-- list of file suffixes and description of the mime type
CREATE TABLE mimetypes (
  id SERIAL PRIMARY KEY,
  name VARCHAR(64) UNIQUE,
  iconUri VARCHAR(256) UNIQUE,
  description TEXT
);

-- list of file extensions associated with mimetype
CREATE TABLE fileextensions (
  extension VARCHAR(8) PRIMARY KEY,
  mimetypeid INTEGER NOT NULL REFERENCES mimetypes(id) ON DELETE CASCADE
);
CREATE INDEX ON fileextensions (mimetypeid);

-- Contains a list of files managed by the CMS.
-- Folder hierarchy is not supported so the file name
-- must be unique within the system
CREATE TABLE files (
  id SERIAL PRIMARY KEY,
  userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  name VARCHAR(64) NOT NULL UNIQUE,
  mimetypeid INTEGER NOT NULL REFERENCES mimetypes(id),
  created TIMESTAMP NOT NULL,
  updated TIMESTAMP NOT NULL,
  created_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  updated_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  CHECK(updated >= created)
);

-- Contains a list of tags, each tag has a name, uri and tag type.
-- Uri must be unique within the tag type. Uri should be directly converted
-- from tag name. Final url of the tag will be somethinf like
-- /tags/{tagtype.uri}/{tag.uri}
CREATE TABLE tags (
  id SERIAL PRIMARY KEY,
  name VARCHAR(32) NOT NULL UNIQUE,
  uri VARCHAR(32) NOT NULL UNIQUE,
  created TIMESTAMP NOT NULL,
  updated TIMESTAMP NOT NULL,
  created_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  updated_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  CHECK(updated >= created)
);

-- Status of the post like for example DRAFT, REVIEW, PUBLISHED.
-- Each status has public flag which determines if the post should
-- be publicly accessible or not.
CREATE TABLE poststatuses (
  name VARCHAR(32) PRIMARY KEY,
  public BOOLEAN NOT NULL DEFAULT FALSE,
  description TEXT
);

INSERT INTO poststatuses (name, public, description) VALUES ('draft', FALSE, 'Post is still in progress, content not finalized.');
INSERT INTO poststatuses (name, public, description) VALUES ('review', FALSE, 'Post content is finalized but in review.');
INSERT INTO poststatuses (name, public, description) VALUES ('published', TRUE, 'Post is published.');

-- Contains post data, each post has title and content which are
-- publicly visible. Title is clear text and content is html.
-- Post source is the source of the content in specified sourcetype.
-- Status is post status which can be changed by the user.
-- Uri must be unique, it is generated from the title but should be
-- settable by the user directly.
CREATE TABLE posts (
  id SERIAL PRIMARY KEY,
  title VARCHAR(128) NOT NULL,
  subtitle VARCHAR(256),
  uri VARCHAR(128) NOT NULL UNIQUE,
  content TEXT,
  markdown TEXT,
  status VARCHAR(32) REFERENCES poststatuses(name) ON DELETE RESTRICT,
  metaData JSON,
  commentsClosed BOOLEAN NOT NULL DEFAULT FALSE,
  created TIMESTAMP NOT NULL,
  updated TIMESTAMP NOT NULL,
  created_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  updated_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  CHECK(updated >= created)
);

-- Contains a list of post actions
CREATE TABLE postactions (
  name VARCHAR(32) PRIMARY KEY,
  description TEXT
);

INSERT INTO postactions (name, description) VALUES ('create', 'Post created');
INSERT INTO postactions (name, description) VALUES ('update', 'Post updated');
INSERT INTO postactions (name, description) VALUES ('status change', 'Post status was changed');
INSERT INTO postactions (name, description) VALUES ('delete', 'Post deleted');

-- Post change log
CREATE TABLE postlog (
  id SERIAL PRIMARY KEY,
  postid INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
  userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  action VARCHAR(32) REFERENCES postactions(name) ON DELETE SET NULL,
  data JSON,
  created TIMESTAMP NOT NULL
);
CREATE INDEX ON postlog(postid);

-- M:N table from posts to tags
CREATE TABLE posttags (
  postid INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
  tagid INTEGER NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
  PRIMARY KEY(postid, tagid)
);

-- List of attachment types
CREATE TABLE attachmenttypes (
  name VARCHAR(32) PRIMARY KEY,
  description TEXT
);
INSERT INTO attachmenttypes (name, description) VALUES ('file', 'File linked to post that will be offered in download section');
INSERT INTO attachmenttypes (name, description) VALUES ('postthumb', 'Post thumbnail that will be visible in post listings');

-- M:N table from posts to files
CREATE TABLE attachments (
  postid INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
  type VARCHAR(32) REFERENCES attachmenttypes(name) ON DELETE SET NULL,
  fileid INTEGER NOT NULL REFERENCES files(id) ON DELETE CASCADE,
  name VARCHAR(128)
);
CREATE INDEX ON attachments(postid);
CREATE INDEX ON attachments(fileid);

CREATE TABLE commentstatuses (
  name VARCHAR(32) PRIMARY KEY,
  public BOOLEAN NOT NULL DEFAULT FALSE,
  description TEXT
);
INSERT INTO commentstatuses (name, public, description) VALUES ('review', FALSE, 'Comment is in review');
INSERT INTO commentstatuses (name, public, description) VALUES ('spam', FALSE, 'Comment is marked as spam');
INSERT INTO commentstatuses (name, public, description) VALUES ('approved', TRUE, 'Comment is marked as approved and is visible publicly');

-- Contains a list of comments
CREATE TABLE comments (
  id SERIAL PRIMARY KEY,
  postid INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
  userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  status VARCHAR(32) REFERENCES commentstatuses(name) ON DELETE RESTRICT,
  commenterEmail VARCHAR(128),
  commenterName VARCHAR(128) NOT NULL,
  content TEXT,
  markdown TEXT,
  created TIMESTAMP NOT NULL,
  updated TIMESTAMP NOT NULL,
  CHECK(updated >= created),
  UNIQUE(commenterName, postid)
);
CREATE INDEX ON comments(postid);

-- Contains categories which is hierarchical taxonomy
CREATE TABLE categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(32) NOT NULL UNIQUE,
  uri VARCHAR(32) NOT NULL UNIQUE,
  description TEXT,
  created TIMESTAMP NOT NULL,
  updated TIMESTAMP NOT NULL,
  created_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  updated_userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  CHECK(updated >= created)
);
INSERT INTO categories (name, uri, description, created, updated, created_userid, updated_userid) VALUES ('General', 'general', 'General category', localtimestamp, localtimestamp, (SELECT id FROM users WHERE email = 'admin@local'), (SELECT id FROM users WHERE email = 'admin@local'));

-- M:N relation between categories and posts
CREATE TABLE postcategories (
  postid INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
  categoryid INTEGER NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
  PRIMARY KEY(postid, categoryid)
);

-- countries table
CREATE TABLE countries (
  code CHAR(2) PRIMARY KEY,
  code3 CHAR(3) UNIQUE,
  numeric_code NUMERIC(3),
  name VARCHAR(128) NOT NULL,
  flagIconUri VARCHAR(128)
);

-- visit statistics table
CREATE TABLE stats (
  id BIGSERIAL PRIMARY KEY,
  tracker VARCHAR(128),
  method VARCHAR(16) NOT NULL,
  uri TEXT NOT NULL,
  accept TEXT,
  accept_language TEXT,
  accept_encoding TEXT,
  referer TEXT,
  user_agent TEXT,
  ipaddr inet NOT NULL,
  userid INTEGER REFERENCES users(id) ON DELETE SET NULL,
  resolvedCountryCode CHAR(2) REFERENCES countries(code) ON DELETE SET NULL,
  postid INTEGER REFERENCES posts(id) ON DELETE SET NULL,
  tagid INTEGER REFERENCES tags(id) ON DELETE SET NULL,
  categoryid INTEGER REFERENCES categories(id) ON DELETE SET NULL,
  ts TIMESTAMP NOT NULL
);

ALTER TABLE attachments OWNER TO website;
ALTER TABLE attachmenttypes OWNER TO website;
ALTER TABLE categories OWNER TO website;
ALTER TABLE comments OWNER TO website;
ALTER TABLE commentstatuses OWNER TO website;
ALTER TABLE countries OWNER TO website;
ALTER TABLE fileextensions OWNER TO website;
ALTER TABLE files OWNER TO website;
ALTER TABLE mimetypes OWNER TO website;
ALTER TABLE postactions OWNER TO website;
ALTER TABLE postcategories OWNER TO website;
ALTER TABLE postlog OWNER TO website;
ALTER TABLE posts OWNER TO website;
ALTER TABLE poststatuses OWNER TO website;
ALTER TABLE posttags OWNER TO website;
ALTER TABLE stats OWNER TO website;
ALTER TABLE tags OWNER TO website;
ALTER TABLE users OWNER TO website;

ALTER SEQUENCE categories_id_seq OWNER TO website;
ALTER SEQUENCE comments_id_seq OWNER TO website;
ALTER SEQUENCE files_id_seq OWNER TO website;
ALTER SEQUENCE mimetypes_id_seq OWNER TO website;
ALTER SEQUENCE postlog_id_seq OWNER TO website;
ALTER SEQUENCE posts_id_seq OWNER TO website;
ALTER SEQUENCE stats_id_seq OWNER TO website;
ALTER SEQUENCE tags_id_seq OWNER TO website;
ALTER SEQUENCE users_id_seq OWNER TO website;