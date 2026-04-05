import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { QuizService } from '../../services/quiz.service';
import { Question, SessionScore } from '../../models/question.model';

type OptionKey = 'A' | 'B' | 'C' | 'D';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './quiz.component.html',
  styleUrl: './quiz.component.scss'
})
export class QuizComponent implements OnInit {
  currentQuestion: Question | null = null;
  currentIndex: number = 0;
  totalQuestions: number = 0;
  selectedOption: OptionKey | null = null;
  answered: boolean = false;
  loading: boolean = false;
  errorMessage: string = '';

  readonly options: OptionKey[] = ['A', 'B', 'C', 'D'];

  constructor(
    private quizService: QuizService,
    private apiService: ApiService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.quizService.getTotalQuestions() === 0) {
      this.router.navigate(['/select']);
      return;
    }
    this.loadQuestion();
  }

  loadQuestion(): void {
    this.currentQuestion = this.quizService.getCurrentQuestion();
    this.currentIndex = this.quizService.getCurrentIndex();
    this.totalQuestions = this.quizService.getTotalQuestions();
    this.selectedOption = null;
    this.answered = false;
    this.errorMessage = '';
  }

  getOptionText(option: OptionKey): string {
    if (!this.currentQuestion) return '';
    const key = `option${option}` as keyof Question;
    return this.currentQuestion[key] as string;
  }

  selectOption(option: OptionKey): void {
    if (this.answered) return;
    this.selectedOption = option;
    this.quizService.answerQuestion(option);
  }

  getOptionClass(option: OptionKey): string {
    if (this.answered && this.currentQuestion) {
      if (option === this.currentQuestion.correctOption) return 'correct';
      if (option === this.selectedOption) return 'wrong';
      return '';
    }
    if (option === this.selectedOption) return 'selected';
    return '';
  }

  getExplanation(): string {
    if (!this.answered || !this.selectedOption || !this.currentQuestion) return '';
    const key = `explanation${this.selectedOption}` as keyof Question;
    return this.currentQuestion[key] as string;
  }

  get progressPercent(): number {
    return ((this.currentIndex + 1) / this.totalQuestions) * 100;
  }

  get isLastQuestion(): boolean {
    return this.quizService.isLastQuestion();
  }

  submitAnswer(): void {
    this.answered = true;
  }

  nextQuestion(): void {
    this.quizService.nextQuestion();
    this.loadQuestion();
  }

  finish(): void {
    const config = this.quizService.getConfig();
    if (!config) return;
    this.loading = true;
    this.apiService.saveSession(config.topic, this.quizService.getAnswers()).subscribe({
      next: (score: SessionScore) => {
        this.router.navigate(['/result'], {
          state: { sessionScore: score, answers: this.quizService.getAnswers() }
        });
      },
      error: (err: Error) => {
        this.errorMessage = err.message || 'Failed to save session.';
        this.loading = false;
      }
    });
  }
}
