package dev.balinapp.ui.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dev.balinapp.data.datasource.comment.CommentPagingSource
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.comment.CommentOutput
import dev.balinapp.domain.usecase.DeleteCommentUseCase
import dev.balinapp.domain.usecase.PostCommentUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentViewModel @Inject constructor(
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val commentPagingSource: CommentPagingSource
) : ViewModel() {

    private val _uploadCommentState =
        MutableStateFlow<RequestResult<CommentOutput>>(RequestResult.Idle)
    val uploadCommentState: StateFlow<RequestResult<CommentOutput>> = _uploadCommentState

    private var _pagingFlow: MutableSharedFlow<PagingData<CommentOutput>> = MutableSharedFlow()
    val pagingFlow: SharedFlow<PagingData<CommentOutput>> = _pagingFlow

    private var _imageState = MutableStateFlow(ImageState())

    fun updateImageState(imageId: Int, url: String, date: Long) {
        _imageState.update {
            it.copy(
                date = date,
                url = url,
                imageId = imageId
            )
        }
    }

    fun loadComments() {
        viewModelScope.launch {
            commentPagingSource.getComments(_imageState.value.imageId).collect {
                _pagingFlow.emit(it)
            }
        }
    }

    fun postComment(comment: String) {
        viewModelScope.launch {
            _uploadCommentState.emit(RequestResult.InProgress())
            _uploadCommentState.emit(
                postCommentUseCase(
                    imageId = _imageState.value.imageId,
                    text = comment
                )
            )
        }
    }

    fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            deleteCommentUseCase(
                imageId = _imageState.value.imageId,
                commentId = commentId
            )
        }
    }
}