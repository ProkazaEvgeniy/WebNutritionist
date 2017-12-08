<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-primary">
	<div class="row profile">
			<div class="col-xs-12 text-center">
				<h4 class="data-header">График количества пищи на 1 месяц</h4>
				<img src="${chartPath.pathOneMonth }" class="img-thumbnail" />
			</div>
			<div class="col-xs-12 text-center">
				<h4 class="data-header">График количества пищи на 6 месяцев</h4>
				<img src="${chartPath.pathSixMonth }" class="img-thumbnail" />
			</div>
		</div>
</div>