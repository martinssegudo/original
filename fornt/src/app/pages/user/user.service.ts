import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, retry } from 'rxjs/operators';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { Pessoa } from 'app/shared/entidades/Pessoa';
import { ToastrService } from 'ngx-toastr';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private API: string = 'http://localhost:8080/pessoa/';
  menssagemErro: BehaviorSubject<string> = new BehaviorSubject<string>('');

  constructor(private http: HttpClient,
              private toast: ToastrService) { }

  salvaPessoa(pessoa: Pessoa): Observable<Pessoa>{
    return this.http.post<Pessoa>(this.API, pessoa, httpOptions)
            .pipe(
                map(
                    pessoa => {
                        return pessoa;
                    }
                ),
                catchError(this.handleError),
            );
  }

  editaPessoa(pessoa: Pessoa): Observable<Pessoa>{
    return this.http.put<Pessoa>(this.API, pessoa, httpOptions)
            .pipe(
                map(
                    pessoa => {
                        return pessoa;
                    }
                ),
                catchError(this.handleError),
            );
  }

  removerpessoa(id): Observable<boolean>{
    return this.http.delete<boolean>(this.API + `/delete/${id}`).pipe(
      retry(2),
      catchError(this.handleError),
  );
  }

  recuperaPessoaProID(id: number): Observable<Pessoa> {
    return this.http.get<Pessoa>(this.API + `${id}`).pipe(
        retry(2),
        catchError(this.handleError),
    );
  }

  recuperaTodasPessoas(): Observable<Pessoa[]> {
    return this.http.get<Pessoa[]>(this.API+'/list/all').pipe(
        retry(2),
        catchError(this.handleError),
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
        // A client-side or network error occurred. Handle it accordingly.
        //this.toast.error(error.error);
        console.error('An error occurred:', error.error.message);
    } else {
        // The backend returned an unsuccessful response code.
        // The response body may contain clues as to what went wrong,
        console.error(
            `Backend returned code ${error.status}, ` +
            `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(error.error);
}
}
