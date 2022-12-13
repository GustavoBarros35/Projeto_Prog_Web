package br.edu.uepb.diario.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

import br.edu.uepb.diario.domain.Turma;

import br.edu.uepb.diario.dto.TurmaDTO;
import br.edu.uepb.diario.exceptions.ExistingObjectSameNameException;
import br.edu.uepb.diario.mappers.TurmaMapper;

import br.edu.uepb.diario.services.TurmaService;

@RestController
@RequestMapping(value = "/turmas")
@Api(value = "Turma")
public class TurmaController {
	
	@Autowired
	private TurmaService turmaService;


	@Autowired
    private TurmaMapper turmaMapper;
	
	
	@GetMapping()
	@ApiOperation(value = "Retorna a lista de todas as turmas cadastrados.")
	public List<TurmaDTO> listarTurmas() {
        List<Turma> turmas = turmaService.ListarTurmas();
        return turmas.stream()
                        .map(turmaMapper::convertToTurmaDTO)
                        .collect(Collectors.toList());
    }
	
	@PostMapping
	@ApiOperation(value = "Cadastra uma nova turma.")
	public ResponseEntity<?> registrarTurma(@RequestBody TurmaDTO turmaDTO) {
        try {
            Turma turma = turmaMapper.convertFromTurmaDTO(turmaDTO);
            return new ResponseEntity<>(turmaService.adicionarTurma(turma), HttpStatus.CREATED);
        } catch (ExistingObjectSameNameException e) {
			return ResponseEntity.badRequest().body("Nome da turma ja Registrada");
        }
    }

	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna uma turma pelo seu {id}.")
	public ResponseEntity<?> buscarTurma(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(turmaMapper.convertToTurmaDTO(turmaService.buscarTurma(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("Turma nao encontrada");
        }
    }

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza os dados de uma turma.")
	public TurmaDTO atualizarTurma(@PathVariable("id") Long id, @RequestBody TurmaDTO turmaDTO) {
        Turma turma = turmaMapper.convertFromTurmaDTO(turmaDTO);
        return turmaMapper.convertToTurmaDTO(turmaService.atualizarTurma(turma));
    }

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta uma turma Cadastrado.")
	public ResponseEntity<Object> deletarTurma(@PathVariable("id") Long id){
		boolean turmaExiste = turmaService.verificarSeTurmaExiste(id);
		
		if(!turmaExiste){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
		}
		turmaService.deletarTurma(id);
		return ResponseEntity.status(HttpStatus.OK).body("Turma Deletada com sucesso");

	}

    //PATCH
	
	@PatchMapping("/{idTurma}/matricularAluno/{idAluno}")
	@ApiOperation(value = "Matricular um aluno a uma turma.")
    public TurmaDTO matricularAluno(@PathVariable("idTurma") Long idTurma,@PathVariable("idAluno") Long idAluno, @RequestBody TurmaDTO turmaDTO) throws ExistingObjectSameNameException, NotFoundException{

        return turmaMapper.convertToTurmaDTO(turmaService.matricularAluno(idTurma, idAluno));

    }


	@PatchMapping("/{idTurma}/vincularProfessor/{idProf}")
	@ApiOperation(value = "Vincula um Professor a uma turma.")
    public TurmaDTO vincularProfessor(@PathVariable("idTurma") Long idTurma,@PathVariable("idProf") Long idProf, @RequestBody TurmaDTO turmaDTO) throws ExistingObjectSameNameException, NotFoundException{
	     
        return turmaMapper.convertToTurmaDTO(turmaService.vincularProfessor(idTurma, idProf));
	
	}


	

}

