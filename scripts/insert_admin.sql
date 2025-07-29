-- ! ! ! NOT FOR PROD admin admin
INSERT INTO public.tech_user(
    id, username, password, enabled)
VALUES (gen_random_uuid (), 'admin', '$2a$10$NrIkBB75zp6uEymWl4oMOOcbooGMBOugJ/4XNN1ZUScxPEmRtHSQC', true);