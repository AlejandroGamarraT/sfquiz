import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { QuizService } from '../../services/quiz.service';
import { SessionScore, Answer, Question } from '../../models/question.model';

@Component({
  selector: 'app-result',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './result.component.html',
  styleUrl: './result.component.scss'
})
export class ResultComponent implements OnInit {
  sessionScore: SessionScore | null = null;
  answers: Answer[] = [];
  retrying: boolean = false;
  errorMessage: string = '';

  constructor(
    private router: Router,
    private quizService: QuizService,
    private apiService: ApiService
  ) {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras?.state as { sessionScore: SessionScore; answers: Answer[] } | undefined;
    if (state) {
      this.sessionScore = state.sessionScore ?? null;
      this.answers = state.answers ?? [];
    }
  }

  ngOnInit(): void {
    if (!this.sessionScore) {
      this.router.navigate(['/select']);
    }
  }

  get scorePercent(): number {
    if (!this.sessionScore || this.sessionScore.totalQuestions === 0) return 0;
    return Math.round((this.sessionScore.correctCount / this.sessionScore.totalQuestions) * 100);
  }

  getUserAnswer(question: Question): Answer | undefined {
    return this.answers.find(a => a.questionId === question.id);
  }

  isCorrect(question: Question): boolean {
    const userAnswer = this.getUserAnswer(question);
    return userAnswer?.selectedOption === question.correctOption;
  }

  getCorrectExplanation(question: Question): string {
    const key = `explanation${question.correctOption}` as keyof Question;
    return question[key] as string;
  }

  retry(): void {
    const config = this.quizService.getConfig();
    if (!config) {
      this.router.navigate(['/select']);
      return;
    }

    this.retrying = true;
    this.errorMessage = '';

    this.apiService.getQuestions(config).subscribe({
      next: (questions) => {
        this.quizService.startQuiz(config, questions);
        this.retrying = false;
        this.router.navigate(['/quiz']);
      },
      error: (err: Error) => {
        this.errorMessage = err.message || 'Failed to load questions.';
        this.retrying = false;
      }
    });
  }

  newQuiz(): void {
    this.quizService.reset();
    this.router.navigate(['/select']);
  }
}
