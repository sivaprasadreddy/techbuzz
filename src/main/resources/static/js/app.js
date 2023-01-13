
function deletePost(id)
{
    let yes = confirm("Are you sure to delete?");
    if (yes) {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax ({
            url: '/posts/'+id,
            type: "DELETE",
            headers: {
              [header]: token
            },
            success: function(responseData, status){
                window.location.reload();
            }
        });
    }

}

function addVote(postId, vote)
{
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $.ajax ({
        url: '/api/votes',
        type: "POST",
        headers: {
            [header]: token
        },
        data: JSON.stringify({postId: postId, value: vote}),
        contentType: "application/json",
        success: function(responseData, status){
            window.location.reload();
        }
    });
}

function initCategoriesAutoComplete(fieldSelector)
{
    $.ajax ({
      url: '/api/categories',
      type: "GET",
      dataType: "json",
      success: function(responseData){
        $(fieldSelector).selectize({
          maxItems: 1,
          valueField: 'id',
          labelField: 'name',
          searchField: 'name',
          options: responseData,
          create: false
        })
      }
  });
}
