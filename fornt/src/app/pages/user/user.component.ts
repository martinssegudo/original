import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Pessoa } from 'app/shared/entidades/Pessoa';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserService } from './user.service';

@Component({
    selector: 'user-cmp',
    moduleId: module.id,
    templateUrl: 'user.component.html'
})

export class UserComponent implements OnInit, OnDestroy{

    formulario: FormGroup;
    pessoaSalvarEditar: Pessoa;
    pessoas: BehaviorSubject<Pessoa[]> = new BehaviorSubject<Pessoa[]>([]);
    private listaPessoasSubscription: Subscription;
    private salvarPessoaSubscription: Subscription;
    private editaPessoaSubscription: Subscription;
    private removerPessoaSubscription: Subscription;
    private lancaErroPessoaSubscription: Subscription;


    constructor(private fb: FormBuilder,
                private userService: UserService,
                private toast: ToastrService){}

    ngOnInit(){
      this.lancaErro();
      this.montaFormGourp();
      this.listaPessoas();
    }

    ngOnDestroy(){
      this.listaPessoasSubscription.unsubscribe();
      this.salvarPessoaSubscription.unsubscribe();
      this.editaPessoaSubscription.unsubscribe();
      this.removerPessoaSubscription.unsubscribe();
    }

    private lancaErro(){
      this.lancaErroPessoaSubscription = this.userService.menssagemErro.subscribe(
        erro => {
          if(erro){
            this.toast.error(erro)
          }

        }
      )
    }

    private listaPessoas(){
      this.listaPessoasSubscription = this.userService.recuperaTodasPessoas().subscribe(
        pessoas => {
          this.pessoas.next(pessoas);
        }
      );
    }

    remover(pessoa){
      this.removerPessoaSubscription = this.userService.removerpessoa(pessoa.id).subscribe(
        confirmacao => {
          if(confirmacao){
            this.toast.success("Pessoa de nome: "+pessoa.nome+" removida com sucesso");
          }else{
            this.toast.error("Erro na remoção da pessoa: "+pessoa.nome);
          }
          this.listaPessoas();
        }
      )
    }

    cadastrar(){
      if(this.pessoaSalvarEditar
          && this.pessoaSalvarEditar.editando){
        this.editar();
      }else{
        this.salvar();
      }

    }

    editClick(pessoa){
      this.formulario = this.fb.group(
        {
          id: [pessoa.id],
          nome: [pessoa.nome, Validators.required],
          cpf: [pessoa.cpf, Validators.required]
        }
      );
    }

    salvar(){
      console.log('salvar')
      this.pessoaSalvarEditar = this.formulario.value;
      this.salvarPessoaSubscription = this.userService.salvaPessoa(this.pessoaSalvarEditar).subscribe(
        pessoa => {
          this.listaPessoas();
          this.formulario.reset();
          this.pessoaSalvarEditar = null;
        },
        erro => {
          this.userService.menssagemErro.next(erro);
        }
      );
    }

    editar(){
      console.log('editar')
      this.pessoaSalvarEditar = this.formulario.value;
      this.editaPessoaSubscription = this.userService.editaPessoa(this.pessoaSalvarEditar).subscribe(
        pessoa => {
          this.listaPessoas();
          this.formulario.reset();
          this.pessoaSalvarEditar = null;
        },
        erro => {
          this.userService.menssagemErro.next(erro);
        }
      )
    }

    private montaFormGourp(){
      this.formulario = this.fb.group(
        {
          nome: [null, Validators.required],
          cpf: [null, Validators.required]
        }
      );
    }
}
