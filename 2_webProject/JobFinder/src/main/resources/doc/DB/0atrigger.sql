
--chat_message trigger
DROP TRIGGER chat_message_before_insert;
CREATE OR REPLACE TRIGGER chat_message_before_insert
BEFORE INSERT ON chat_message
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT chat_message_seq.NEXTVAL INTO :NEW.id FROM dual;
    END IF;
END;
/