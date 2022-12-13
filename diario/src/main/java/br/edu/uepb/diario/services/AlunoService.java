package br.edu.uepb.diario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.uepb.diario.domain.Aluno;
import br.edu.uepb.diario.exceptions.ExistingObjectSameNameException;
import br.edu.uepb.diario.repositories.AlunoRepository;
import javassist.NotFoundException;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	public List<Aluno> ListarAlunos (){
		return alunoRepository.findAll();
	}

	public Aluno adicionarAluno(Aluno aluno) throws ExistingObjectSameNameException{
		if (alunoRepository.findByNome(aluno.getNome()) != null)
            throw new ExistingObjectSameNameException("Já existe um aluno com esse nome!");
		return alunoRepository.save(aluno);
	}

	public Aluno buscarAluno(Long alunoID) throws NotFoundException{
		return alunoRepository.findById(alunoID).orElseThrow(() -> new NotFoundException("Não existe um aluno com esse id!"));
	}

	public boolean verificarSeAlunoExiste(Long alunoID){
		return alunoRepository.existsById(alunoID);
	}

	public Aluno atualizarAluno(Long id, Aluno aluno){
		aluno.setId(id);

		return alunoRepository.save(aluno);
		
	}

	public void deletarAluno(Long alunoID){
		Aluno alunoToDelete = alunoRepository.findById(alunoID).get();
        alunoRepository.delete(alunoToDelete);
	}

}

