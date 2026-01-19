-- Seed data for the MySQL "persist" profile.
INSERT IGNORE INTO `address` (`id`, `street`, `city`, `state`, `number`, `zip_code`, `owner_id`, `project_id`) VALUES
  (1, 'Owner Street 1', 'Sao Paulo', 'SP', '101', '01001-000', NULL, NULL),
  (2, 'Owner Street 2', 'Sao Paulo', 'SP', '102', '01002-000', NULL, NULL),
  (3, 'Owner Street 3', 'Sao Paulo', 'SP', '103', '01003-000', NULL, NULL),
  (4, 'Project Avenue 1', 'Campinas', 'SP', '501', '13011-100', NULL, NULL),
  (5, 'Project Avenue 2', 'Campinas', 'SP', '502', '13012-100', NULL, NULL),
  (6, 'Project Avenue 3', 'Campinas', 'SP', '503', '13013-100', NULL, NULL);

INSERT IGNORE INTO `project` (`id`, `project_name`, `address_id`) VALUES
  (1, 'Atlas Migration', 4),
  (2, 'Nimbus Analytics', 5),
  (3, 'Orion Console', 6);

INSERT IGNORE INTO `owner` (`id`, `name`, `email`, `address_id`) VALUES
  (1, 'Ana Souza', 'ana.souza@example.com', 1),
  (2, 'Bruno Lima', 'bruno.lima@example.com', 2),
  (3, 'Carla Mendes', 'carla.mendes@example.com', 3);

UPDATE `address` SET `owner_id` = 1 WHERE `id` = 1;
UPDATE `address` SET `owner_id` = 2 WHERE `id` = 2;
UPDATE `address` SET `owner_id` = 3 WHERE `id` = 3;
UPDATE `address` SET `project_id` = 1 WHERE `id` = 4;
UPDATE `address` SET `project_id` = 2 WHERE `id` = 5;
UPDATE `address` SET `project_id` = 3 WHERE `id` = 6;

INSERT IGNORE INTO `owner_project` (`owner_id`, `project_id`) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (2, 3),
  (3, 1);

INSERT IGNORE INTO `file` (`id`, `filename`, `path`, `project_id`) VALUES
  (1, 'file-001.txt', '/projects/atlas-migration/docs/file-001.txt', 1),
  (2, 'file-002.txt', '/projects/atlas-migration/docs/file-002.txt', 1),
  (3, 'file-003.txt', '/projects/nimbus-analytics/docs/file-003.txt', 2),
  (4, 'file-004.txt', '/projects/orion-console/docs/file-004.txt', 3);
