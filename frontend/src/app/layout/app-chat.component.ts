import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-chat',
  templateUrl: './app-chat.component.html',
  styleUrls: ['./app-chat.component.scss'],
})
export class AppChatComponent {
  userInput = '';
  messages: { text: string; isUser: boolean }[] = [];
  isLoading = false;

  private apiUrl = 'http://localhost:3000/chat';

  constructor(private http: HttpClient) {}

  async sendMessage(event?: KeyboardEvent) {
    if (event) {
      event.preventDefault();
    }

    if (!this.userInput.trim()) {
      alert('Please enter a question about books!');
      return;
    }

    this.messages.push({ text: this.userInput, isUser: true });

    this.isLoading = true;

    try {
      const data = await this.http
        .post<any>(this.apiUrl, { userInput: this.userInput })
        .toPromise();

      if (data && data.response) {
        this.messages.push({ text: data.response, isUser: false });
      } else {
        this.messages.push({
          text: 'No response from the server. Please try again.',
          isUser: false,
        });
      }
    } catch (error) {
      console.error('Error:', error);
      this.messages.push({
        text: 'Something went wrong. Please try again.',
        isUser: false,
      });
    } finally {
      this.isLoading = false;
      this.userInput = '';
    }
  }
}
