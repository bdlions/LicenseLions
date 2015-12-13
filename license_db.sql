CREATE TABLE IF NOT EXISTS `license_keys` (
  `key` varchar(200) NOT NULL,
  `is_used` boolean DEFAULT FALSE,
  `created_on` int(11) unsigned DEFAULT 0,
  `modified_on` int(11) unsigned DEFAULT 0,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;
INSERT INTO `license_keys` (`key`, `created_on`) VALUES
('key1', 1447845678),
('key2', 1447845678),
('key3', 1447845678);

CREATE TABLE IF NOT EXISTS `licenses` (
  `key` varchar(200) NOT NULL,  
  `subscription_date` int(11) unsigned NOT NULL,
  `start_date` int(11) unsigned NOT NULL,
  `evolution_period` int(11) unsigned NOT NULL,
  `mac_address` varchar(200) NOT NULL, 
  `cpu_address` varchar(200) NOT NULL, 
  `processor_address` varchar(200) NOT NULL, 
  PRIMARY KEY (`key`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;
ALTER TABLE `licenses`
  ADD CONSTRAINT `fk_licenses_license_keys1` FOREIGN KEY (`key`) REFERENCES `license_keys` (`key`) ON DELETE CASCADE ON UPDATE CASCADE;
INSERT INTO `licenses` (`key`, `subscription_date`, `start_date`, `evolution_period`, `mac_address`, `cpu_address`, `processor_address`) VALUES
('key1', 1447845678, 1447845678, 1447845678, 'mac1', 'cpu1', 'processor1');
