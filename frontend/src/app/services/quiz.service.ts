import { Injectable } from '@angular/core';
import { Question, Answer } from '../models/question.model';
import { QuizConfig } from '../models/session.model';

@Injectable({ providedIn: 'root' })
export class QuizService {
  private config: QuizConfig | null = null;
  private questions: Question[] = [];
  private currentIndex: number = 0;
  private answers: Answer[] = [];

  startQuiz(config: QuizConfig, questions: Question[]): void {
    this.config = config;
    this.questions = questions;
    this.currentIndex = 0;
    this.answers = [];
  }

  getConfig(): QuizConfig | null {
    return this.config;
  }

  getQuestions(): Question[] {
    return this.questions;
  }

  getCurrentQuestion(): Question | null {
    return this.questions[this.currentIndex] ?? null;
  }

  getCurrentIndex(): number {
    return this.currentIndex;
  }

  getTotalQuestions(): number {
    return this.questions.length;
  }

  answerQuestion(option: 'A' | 'B' | 'C' | 'D'): void {
    const q = this.getCurrentQuestion();
    if (!q) return;
    this.answers[this.currentIndex] = { questionId: q.id, selectedOption: option };
  }

  getCurrentAnswer(): Answer | null {
    return this.answers[this.currentIndex] ?? null;
  }

  nextQuestion(): void {
    if (!this.isLastQuestion()) {
      this.currentIndex++;
    }
  }

  previousQuestion(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    }
  }

  goToFirst(): void {
    this.currentIndex = 0;
  }

  isLastQuestion(): boolean {
    return this.currentIndex === this.questions.length - 1;
  }

  getAnswers(): Answer[] {
    return this.answers;
  }

  reset(): void {
    this.config = null;
    this.questions = [];
    this.currentIndex = 0;
    this.answers = [];
  }
}
