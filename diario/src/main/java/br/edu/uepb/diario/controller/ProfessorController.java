package br.edu.uepb.diario.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

import br.edu.uepb.diario.domain.Professor;
import br.edu.uepb.diario.dto.ProfessorDTO;
import br.edu.uepb.diario.exceptions.ExistingObjectSameNameException;
import br.edu.uepb.diario.mappers.ProfessorMapper;
import br.edu.uepb.diario.services.ProfessorService;

@RestController
@RequestMapping(value = "/professores")
@Api(value = "Professor")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;

	@Autowired
    private ProfessorMapper professorMapper;
	
	@GetMapping()
	@ApiOperation(value = "Retorna a lista de todos os professores cadastrados.")
	public List<ProfessorDTO> listarProfessors() {
        List<Professor> professors = professorService.ListarProfessor();
        return professors.stream()
						.map(professorMapper::convertToProfessorDTO)
                        .collect(Collectors.toList());
    }
	
	@PostMapping
	@ApiOperation(value = "Cadastra um novo professor.")
	public ResponseEntity<?> registrarProfessor(@RequestBody ProfessorDTO professorDTO) throws ExistingObjectSameNameException{
		try {
            Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
            return new ResponseEntity<>(professorService.adicionarProfessor(professor), HttpStatus.CREATED);

        } catch (ExistingObjectSameNameException e) {
            return ResponseEntity.badRequest().body("Nome do professor ja Registrado");
        }
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna um professor pelo seu {id}.")
	public ResponseEntity<?> buscarProfessor(@PathVariable Long id){
		try {
            return new ResponseEntity<>(professorMapper.convertToProfessorDTO(professorService.buscarProfessor(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("Professor não encontrado");
        }
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza os dados de um professor.")
	public ProfessorDTO atualizarProfessor(@PathVariable("id") Long id, @RequestBody ProfessorDTO professorDTO){
		Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
        return professorMapper.convertToProfessorDTO(professorService.atualizarProfessor(id, professor));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta um professor cadastrado.")
	public ResponseEntity<Object> deletarProfessor(@PathVariable("id") Long id){
		boolean professorExiste = professorService.verificarSeProfessorExiste(id);
		
		if(!professorExiste){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado");
		}
		professorService.deletarProfessor(id);
		return ResponseEntity.status(HttpStatus.OK).body("Professor Deletado com sucesso");

	}
	
	

}


