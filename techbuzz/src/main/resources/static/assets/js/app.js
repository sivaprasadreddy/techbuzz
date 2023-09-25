
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
        url: '/partials/add-vote',
        type: "POST",
        headers: {
            [header]: token
        },
        data: JSON.stringify({postId: postId, value: vote}),
        contentType: "application/json",
        success: function(responseData, status){
           $("#post-container-"+postId).replaceWith(responseData);
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
const getUserProfile = (userId) => {
    $.ajax({
        url: '/api/userProfile/' + userId,
        type: "GET",
        dataType: "json",
        success: function(responseData) {
            $("#userNameTxt").innerHTML(responseData.name);
            $("#")
        }
    });
}

const tabChangeHandler = (tabName, dataUrl) => {
    const contentPane = $("#" + tabName + "TabContent");
    contentPane.load(dataUrl, function(result) {
        var tabElement = $('#' + tabName);
        var tab = new bootstrap.Tab(tabElement);
        tab.show();
    });

}
const checkPassword =()=>{
    if($("#password").val()!==$("#confirmedPassword").val()){
        $("#passwordMismatchError").show();
        return false;
    }
    $("#passwordMismatchError").hide();
    return true;
}
