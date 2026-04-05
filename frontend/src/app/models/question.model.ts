export interface Question {
  id: number;
  topic: string;
  subtopic: string;
  questionText: string;
  optionA: string;
  optionB: string;
  optionC: string;
  optionD: string;
  correctOption: 'A' | 'B' | 'C' | 'D';
  explanationA: string;
  explanationB: string;
  explanationC: string;
  explanationD: string;
}

export interface Answer {
  questionId: number;
  selectedOption: 'A' | 'B' | 'C' | 'D';
}

export interface SessionScore {
  correctCount: number;
  totalQuestions: number;
  answeredQuestions: Question[];
}
