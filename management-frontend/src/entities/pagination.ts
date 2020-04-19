export interface PaginatedResult<ContentType> {
    content: Array<ContentType>,
    pageable: {
        pageNumber: number
    }
    /**
     * size of the page
     */
    size: number,
    totalPages: number,
    /**
     * total number of items
     */
    totalElements: number
}

export interface PaginatedCollection<ContentType> {
    content: Array<ContentType>
    pageSize: number,
    totalElements: number
    currentPage: number
}