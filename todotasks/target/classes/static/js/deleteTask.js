  $(document).ready(function () {
        $("#deleteBtn").click(function () {
            $.ajax({
                url: "/deleted",
                type: "DELETE",
                data: { id: getId },
                success: function () {
                  
                    console.log("dap");
                },
                error: function () {
                    console.log("nup");
                }

            });
        });
    });