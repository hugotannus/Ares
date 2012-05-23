INSERT INTO material (service_ID, name, sponsor)
	VALUES	(219,'material 1', 'Joao'),
		(12, 'material 2', 'Joao'),
		(12, 'material 3', 'Jose'),
		(12, 'material 4', 'Paulo'),
		(12, 'material 5', 'Antonio'),
		(219,'material 6', 'Jose'),
		(219,'material 7', 'Jose'),
		(219,'material 8', 'Antonio'),
		(219,'material 9', 'Antonio');

INSERT INTO workmanship (service_ID, name, sponsor)
	VALUES	(12, 'mao-de-obra 1', 'Joao'),
		(12, 'mao-de-obra 2', 'Joao'),
		(12, 'mao de obra 3', 'Joao'),
		(219,'mao de obra 4', 'Jose'),
		(219,'mao de obra 5', 'Antonio');

INSERT INTO project (service_ID, name, sponsor)
	VALUES	(12, 'projeto 1', 'Adriano'),
		(219,'projeto 2', 'Adriano');
	
INSERT INTO logistic (service_ID, name, sponsor)
	VALUES	(12, 'Linha de base', 'Luis Henrique'),
		(219,'Linha de base', 'Luis Henrique');

