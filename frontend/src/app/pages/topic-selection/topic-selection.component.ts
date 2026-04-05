import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { QuizService } from '../../services/quiz.service';
import { QuizConfig, TopicInfo } from '../../models/session.model';

@Component({
  selector: 'app-topic-selection',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './topic-selection.component.html',
  styleUrl: './topic-selection.component.scss'
})
export class TopicSelectionComponent implements OnInit {
  topics: TopicInfo[] = [];
  selectedTopic: string = '';
  selectedLimit: number = 10;
  loading: boolean = false;
  starting: boolean = false;
  errorMessage: string = '';

  readonly limits: number[] = [5, 10, 15, 20];

  constructor(
    private apiService: ApiService,
    private quizService: QuizService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.apiService.getTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
        this.loading = false;
      },
      error: (err: Error) => {
        this.errorMessage = err.message || 'Failed to load topics.';
        this.loading = false;
      }
    });
  }

  get selectedTopicCount(): number {
    return this.topics.find(t => t.topic === this.selectedTopic)?.count ?? Infinity;
  }

  onTopicChange(): void {
    if (this.selectedLimit > this.selectedTopicCount) {
      this.selectedLimit = this.limits.filter(l => l <= this.selectedTopicCount).at(-1) ?? this.limits[0];
    }
  }

  setLimit(limit: number): void {
    this.selectedLimit = limit;
  }

  startQuiz(): void {
    if (!this.selectedTopic || this.starting) return;

    const config: QuizConfig = {
      topic: this.selectedTopic,
      limit: this.selectedLimit
    };

    this.starting = true;
    this.errorMessage = '';

    this.apiService.getQuestions(config).subscribe({
      next: (questions) => {
        this.quizService.startQuiz(config, questions);
        this.starting = false;
        this.router.navigate(['/quiz']);
      },
      error: (err: Error) => {
        this.errorMessage = err.message || 'Failed to load questions.';
        this.starting = false;
      }
    });
  }
}
