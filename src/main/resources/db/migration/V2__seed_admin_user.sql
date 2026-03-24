INSERT INTO users (id, full_name, email, password_hash, active, created_at, updated_at)
VALUES (
    '2b1d91aa-7c3b-4d29-816e-f2ffb2b3e9da',
    'Administrador SIGEPLAN',
    'admin@sigeplan.local',
    '$2a$10$2y1V1luEw6wJwbf5sQ2q3.h6ot4Jt6Wc7wZxP0RduCMsZ/HXXIQK6',
    TRUE,
    NOW(),
    NOW()
)
ON CONFLICT (email) DO NOTHING;

INSERT INTO user_roles (user_id, role)
VALUES ('2b1d91aa-7c3b-4d29-816e-f2ffb2b3e9da', 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;
