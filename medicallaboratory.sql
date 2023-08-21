-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 22-Ago-2023 às 00:16
-- Versão do servidor: 10.4.27-MariaDB
-- versão do PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `medicallaboratory`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `agendamentos`
--

CREATE TABLE `agendamentos` (
  `ID` int(11) NOT NULL,
  `PacienteID` int(11) DEFAULT NULL,
  `MedicoID` int(11) DEFAULT NULL,
  `ExameID` int(11) DEFAULT NULL,
  `DataHoraAgendamento` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `agendamentos`
--

INSERT INTO `agendamentos` (`ID`, `PacienteID`, `MedicoID`, `ExameID`, `DataHoraAgendamento`) VALUES
(1, 1, 1, 1, '2023-08-10 10:00:00'),
(2, 1, 1, 1, '2023-08-10 10:10:00'),
(3, 1, 1, 1, '2023-08-10 09:50:00'),
(4, 1, 1, 1, '2023-08-10 10:15:00'),
(5, 2, 1, 1, '2023-08-10 11:40:00'),
(6, 2, 1, 1, '2023-08-10 12:00:00'),
(7, 1, 1, 1, '2023-08-10 12:20:00'),
(8, 4, 1, 1, '2023-08-11 15:00:00'),
(9, 5, 1, 2, '2023-08-12 10:00:00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `exames`
--

CREATE TABLE `exames` (
  `ID` int(11) NOT NULL,
  `NomeExame` varchar(100) DEFAULT NULL,
  `TipoExame` varchar(50) DEFAULT NULL,
  `Preco` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `exames`
--

INSERT INTO `exames` (`ID`, `NomeExame`, `TipoExame`, `Preco`) VALUES
(1, 'Coleta de sangue', 'Sanguíneo', '50.00'),
(2, 'Tomografia do crânio', 'Tomografia', '100.00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `medicos`
--

CREATE TABLE `medicos` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) DEFAULT NULL,
  `Especialidade` varchar(100) DEFAULT NULL,
  `Telefone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `medicos`
--

INSERT INTO `medicos` (`ID`, `Nome`, `Especialidade`, `Telefone`) VALUES
(1, 'Dr Rey', 'Plastica', '999999999');

-- --------------------------------------------------------

--
-- Estrutura da tabela `pacientes`
--

CREATE TABLE `pacientes` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) DEFAULT NULL,
  `DataNascimento` date DEFAULT NULL,
  `Genero` varchar(10) DEFAULT NULL,
  `Telefone` varchar(20) DEFAULT NULL,
  `Endereco` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `pacientes`
--

INSERT INTO `pacientes` (`ID`, `Nome`, `DataNascimento`, `Genero`, `Telefone`, `Endereco`) VALUES
(1, 'Daniel', '1998-10-27', 'Masculino', '984237101', 'Estrada dos Bandeirantes, 8505'),
(2, 'Elsa', '1964-02-19', 'Feminino', '96820548', 'Sarmento Leite, 1423'),
(3, 'Larisa', '2008-08-08', 'Feminino', '999999999', 'Garibaldi'),
(4, 'Pedro', '1956-04-22', 'Masculino', '9823498163', 'Sarmento Leite, 1423'),
(5, 'Rafaela', '2004-10-28', 'Feminino', '951645832284', 'Sarmento Leite, 1423');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `agendamentos`
--
ALTER TABLE `agendamentos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `PacienteID` (`PacienteID`),
  ADD KEY `MedicoID` (`MedicoID`),
  ADD KEY `ExameID` (`ExameID`);

--
-- Índices para tabela `exames`
--
ALTER TABLE `exames`
  ADD PRIMARY KEY (`ID`);

--
-- Índices para tabela `medicos`
--
ALTER TABLE `medicos`
  ADD PRIMARY KEY (`ID`);

--
-- Índices para tabela `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `agendamentos`
--
ALTER TABLE `agendamentos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `exames`
--
ALTER TABLE `exames`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `medicos`
--
ALTER TABLE `medicos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `pacientes`
--
ALTER TABLE `pacientes`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `agendamentos`
--
ALTER TABLE `agendamentos`
  ADD CONSTRAINT `agendamentos_ibfk_1` FOREIGN KEY (`PacienteID`) REFERENCES `pacientes` (`ID`),
  ADD CONSTRAINT `agendamentos_ibfk_2` FOREIGN KEY (`MedicoID`) REFERENCES `medicos` (`ID`),
  ADD CONSTRAINT `agendamentos_ibfk_3` FOREIGN KEY (`ExameID`) REFERENCES `exames` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
