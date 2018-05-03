
create schema ormenhance;

/* ---------------------------------------------------------------------- */
/* Add sequences                                                          */
/* ---------------------------------------------------------------------- */
CREATE SEQUENCE ormenhance.org_seq INCREMENT 1 MINVALUE 1 START 1 CACHE 10;
CREATE SEQUENCE ormenhance.user_seq INCREMENT 1 MINVALUE 1 START 1 CACHE 10;

/* ---------------------------------------------------------------------- */
/* Add table "ormenhance.organization"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE ormenhance.organization (
    id INTEGER DEFAULT nextval('ormenhance.org_seq')  NOT NULL,
    name CHARACTER VARYING(128)  NOT NULL,
    primary_user_id INTEGER,
    enabled BOOLEAN DEFAULT true  NOT NULL,
    logo BYTEA,
    CONSTRAINT organization_PK PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "ormenhance.user"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE ormenhance.user (
    id INTEGER DEFAULT nextval('ormenhance.user_seq')  NOT NULL,
    language CHARACTER(2)  NOT NULL,
    organization_id INTEGER  NOT NULL,
    first_name CHARACTER VARYING(80)  NOT NULL,
    last_name CHARACTER VARYING(80)  NOT NULL,
    enabled BOOLEAN DEFAULT true  NOT NULL,
    CONSTRAINT user_PK PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */
ALTER TABLE ormenhance.user ADD CONSTRAINT organization_user 
    FOREIGN KEY (organization_id) REFERENCES ormenhance.organization (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ormenhance.organization ADD CONSTRAINT user_organization 
    FOREIGN KEY (primary_user_id) REFERENCES ormenhance.user (id);
    
/* ---------------------------------------------------------------------- */
/* Add default organization and user                                      */ 
/* ---------------------------------------------------------------------- */
INSERT INTO ormenhance.organization (name) VALUES('ROOTORG');
INSERT INTO ormenhance.user ( organization_id, language, first_name, last_name, enabled)
                VALUES ( (select o.id from ormenhance.organization o where o.name = 'ROOTORG'),'en','System','Admin',true);
UPDATE ormenhance.organization SET primary_user_id = (select id from ormenhance.user where first_name = 'System') WHERE name='ROOTORG';

GRANT USAGE ON SCHEMA ormenhance, public TO jluo;
GRANT USAGE ON SEQUENCE ormenhance.org_seq, ormenhance.user_seq TO jluo;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE ormenhance.organization, ormenhance.user TO jluo;


    