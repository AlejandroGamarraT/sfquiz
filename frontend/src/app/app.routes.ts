import { Routes } from '@angular/router';
import { TopicSelectionComponent } from './pages/topic-selection/topic-selection.component';
import { QuizComponent } from './pages/quiz/quiz.component';
import { ResultComponent } from './pages/result/result.component';

export const routes: Routes = [
  { path: '', redirectTo: 'select', pathMatch: 'full' },
  { path: 'select', component: TopicSelectionComponent },
  { path: 'quiz', component: QuizComponent },
  { path: 'result', component: ResultComponent }
];
