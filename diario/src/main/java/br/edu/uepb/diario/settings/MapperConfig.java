package br.edu.uepb.diario.settings;


import br.edu.uepb.diario.mappers.AlunoMapper;
import br.edu.uepb.diario.mappers.ProfessorMapper;
import br.edu.uepb.diario.mappers.TurmaMapper;
import br.edu.uepb.diario.mappers.UserMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AlunoMapper alunoMapper() {
        return new AlunoMapper();
    }

    @Bean
    public ProfessorMapper professorMapper() {
        return new ProfessorMapper();
    }

    @Bean
    public TurmaMapper turmaMapper() {
        return new TurmaMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

}

