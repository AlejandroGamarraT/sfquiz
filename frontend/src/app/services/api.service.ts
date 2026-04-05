import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Question, Answer, SessionScore } from '../models/question.model';
import { QuizConfig, TopicInfo } from '../models/session.model';

interface ApiResponse<T> {
  data: T;
  error: string | null;
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}

  getTopics(): Observable<TopicInfo[]> {
    return this.http.get<ApiResponse<TopicInfo[]>>('/api/topics').pipe(
      map(res => {
        if (res.error) throw new Error(res.error);
        return res.data;
      })
    );
  }

  getQuestions(config: QuizConfig): Observable<Question[]> {
    return this.http.get<ApiResponse<{ questions: Question[] }>>('/api/quiz', {
      params: {
        topic: config.topic,
        limit: config.limit.toString()
      }
    }).pipe(
      map(res => {
        if (res.error) throw new Error(res.error);
        return res.data.questions;
      })
    );
  }

  saveSession(topic: string, answers: Answer[]): Observable<SessionScore> {
    return this.http.post<ApiResponse<SessionScore>>('/api/quiz/session', {
      topic,
      answers
    }).pipe(
      map(res => {
        if (res.error) throw new Error(res.error);
        return res.data;
      })
    );
  }
}
