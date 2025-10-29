import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookDto } from 'src/app/shared/model/book/Book.model';
import { BookCriteria } from 'src/app/shared/criteria/book/BookCriteria.model';
import { BookClientService } from "../../../shared/service/client/book/BookClient.service";
import { LayoutService } from 'src/app/layout/service/app.layout.service';

@Component({
    selector: 'app-guest-books-list-admin',
    templateUrl: './guest-books-list-admin.component.html',
    styleUrls: ['./guest-books-list-admin.component.css']
})
export class GuestBooksListAdminComponent implements OnInit {
    books: BookDto[] = [];
    isLoading = true;

    // Pagination variables
    currentPage = 0;
    totalPages = 0;
    pageSize = 6;

    selectedBook: BookDto | null = null; // For the modal description

    constructor(private bookClientService: BookClientService, private router: Router,public layoutService: LayoutService) {}

    ngOnInit(): void {
        this.loadBooks(this.currentPage);
        this.changeTheme('lara-dark-blue', 'dark');
    }

    // Load books using pagination
    loadBooks(page: number): void {
        this.isLoading = true;
        const criteria: BookCriteria = new BookCriteria();
        criteria.page = page;
        criteria.maxResults = this.pageSize;
        criteria.available = true;

        this.bookClientService.findPaginatedByCriteria(criteria).subscribe({
            next: (data) => {
                this.books = data.list;
                this.totalPages = Math.ceil(data.dataSize / this.pageSize);
                this.currentPage = page;
                this.isLoading = false;
            },
            error: (err) => {
                console.error('Error fetching paginated books:', err);
                this.isLoading = false;
            }
        });
    }

    // Navigate to the next page
    goToNextPage(): void {
        if (this.currentPage + 1 < this.totalPages) {
            this.loadBooks(this.currentPage + 1);
        }
    }

    // Navigate to the previous page
    goToPreviousPage(): void {
        if (this.currentPage > 0) {
            this.loadBooks(this.currentPage - 1);
        }
    }

    // Navigate to login and register
    navigateToLogin(): void {
        this.router.navigate(['/admin/login']);
    }

    navigateToRegister(): void {
        this.router.navigate(['/admin/register']);
    }

    // Show modal for book description
    showDescription(book: BookDto): void {
        this.selectedBook = book;
    }

    // Close modal
    closeDescription(): void {
        this.selectedBook = null;
    }

    addToCart(book: BookDto): void {
        // Redirect to login page for unauthorized users
        this.router.navigate(['/admin/login']);
    }

    changeTheme(theme: string, colorScheme: string) {
        const themeLink = <HTMLLinkElement>document.getElementById('theme-css');
        const newHref = themeLink.getAttribute('href')!.replace(this.layoutService.config.theme, theme);
        this.layoutService.config.colorScheme
        this.replaceThemeLink(newHref, () => {
            this.layoutService.config.theme = theme;
            this.layoutService.config.colorScheme = colorScheme;
            this.layoutService.onConfigUpdate();
        });
    }

    replaceThemeLink(href: string, onComplete: Function) {
        const id = 'theme-css';
        const themeLink = <HTMLLinkElement>document.getElementById('theme-css');
        const cloneLinkElement = <HTMLLinkElement>themeLink.cloneNode(true);

        cloneLinkElement.setAttribute('href', href);
        cloneLinkElement.setAttribute('id', id + '-clone');

        themeLink.parentNode!.insertBefore(cloneLinkElement, themeLink.nextSibling);

        cloneLinkElement.addEventListener('load', () => {
            themeLink.remove();
            cloneLinkElement.setAttribute('id', id);
            onComplete();
        });
    }

}
